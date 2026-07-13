package com.example.E_Commerce.service.impl;

import com.example.E_Commerce.dto.order.OrderItemResponse;
import com.example.E_Commerce.dto.order.OrderRequest;
import com.example.E_Commerce.dto.order.OrderResponse;
import com.example.E_Commerce.exception.ResourceNotFoundException;
import com.example.E_Commerce.model.*;
import com.example.E_Commerce.repo.CartRepository;
import com.example.E_Commerce.repo.OrderRepository;
import com.example.E_Commerce.service.CartService;
import com.example.E_Commerce.service.EmailService;
import com.example.E_Commerce.service.OrderService;
import com.example.E_Commerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.E_Commerce.repo.ProductRepository;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final UserService userService;
    private final CartService cartService;
    private final EmailService emailService;
    private final ProductRepository productRepository;

    @Override
    public OrderResponse placeOrder(OrderRequest request) {

        User user = userService.getCurrentUser();

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart not found"));

        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Orders order = new Orders();

        order.setUser(user);
        order.setShippingAddress(request.getShippingAddress());
        order.setStatus(OrderStatus.PENDING);

        BigDecimal total = BigDecimal.ZERO;

        for (CartItem item : cart.getItems()) {

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(item.getProduct())
                    .quantity(item.getQuantity())
                    .priceAtPurchase(item.getProduct().getPrice())
                    .build();

            order.getItems().add(orderItem);

            total = total.add(
                    item.getProduct()
                            .getPrice()
                            .multiply(BigDecimal.valueOf(item.getQuantity()))
            );
            Product product = item.getProduct();

            if (product.getStock() < item.getQuantity()) {
                throw new RuntimeException(
                        product.getName() + " is out of stock"
                );
            }

            product.setStock(
                    product.getStock() - item.getQuantity()
            );

            productRepository.save(product);
        }

        order.setTotalAmount(total);

        // Save Order
        Orders savedOrder = orderRepository.save(order);

        // Send Email
        emailService.sendOrderConfirmation(
                user.getEmail(),
                String.valueOf(savedOrder.getId())
        );

        // Clear Cart
        cartService.clearCart();

        // Return Response
        return mapToResponse(savedOrder);
    }

    @Override
    public List<OrderResponse> getMyOrders() {

        User user = userService.getCurrentUser();

        return orderRepository.findByUser(user)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public OrderResponse getOrderById(Long id) {

        Orders order = orderRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found"));

        return mapToResponse(order);
    }

    @Override
    public void cancelOrder(Long id) {

        Orders order = orderRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found"));

        order.setStatus(OrderStatus.CANCELLED);

        orderRepository.save(order);
    }

    private OrderResponse mapToResponse(Orders order) {

        List<OrderItemResponse> items = order.getItems()
                .stream()
                .map(item -> OrderItemResponse.builder()
                        .productId(item.getProduct().getId())
                        .productName(item.getProduct().getName())
                        .imageUrl(item.getProduct().getImageUrl())
                        .price(item.getPriceAtPurchase())
                        .quantity(item.getQuantity())
                        .subtotal(
                                item.getPriceAtPurchase()
                                        .multiply(BigDecimal.valueOf(item.getQuantity()))
                        )
                        .build())
                .toList();

        return OrderResponse.builder()
                .orderId(order.getId())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .shippingAddress(order.getShippingAddress())
                .createdAt(order.getCreatedAt())
                .items(items)
                .build();
    }
}