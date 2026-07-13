package com.example.E_Commerce.service.impl;

import com.example.E_Commerce.dto.cart.AddToCartRequest;
import com.example.E_Commerce.dto.cart.CartItemResponse;
import com.example.E_Commerce.dto.cart.CartResponse;
import com.example.E_Commerce.dto.cart.UpdateCartRequest;
import com.example.E_Commerce.exception.ResourceNotFoundException;
import com.example.E_Commerce.model.Cart;
import com.example.E_Commerce.model.CartItem;
import com.example.E_Commerce.model.Product;
import com.example.E_Commerce.model.User;
import com.example.E_Commerce.repo.CartItemRepository;
import com.example.E_Commerce.repo.CartRepository;
import com.example.E_Commerce.repo.ProductRepository;
import com.example.E_Commerce.service.CartService;
import com.example.E_Commerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserService userService;

    @Override
    public CartResponse addToCart(AddToCartRequest request) {

        User user = userService.getCurrentUser();

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        CartItem cartItem = cartItemRepository
                .findByCartAndProduct(cart, product)
                .orElse(null);

        if (cartItem != null) {

            cartItem.setQuantity(
                    cartItem.getQuantity() + request.getQuantity()
            );

        } else {

            cartItem = CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(request.getQuantity())
                    .build();

            cart.getItems().add(cartItem);
        }

        cartItemRepository.save(cartItem);

        return getCart();
    }

    @Override
    public CartResponse getCart() {

        User user = userService.getCurrentUser();

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        List<CartItemResponse> items = cart.getItems()
                .stream()
                .map(item -> CartItemResponse.builder()
                        .id(item.getId())
                        .productId(item.getProduct().getId())
                        .productName(item.getProduct().getName())
                        .imageUrl(item.getProduct().getImageUrl())
                        .price(item.getProduct().getPrice())
                        .quantity(item.getQuantity())
                        .subtotal(
                                item.getProduct()
                                        .getPrice()
                                        .multiply(BigDecimal.valueOf(item.getQuantity()))
                        )
                        .build())
                .toList();

        BigDecimal total = items.stream()
                .map(CartItemResponse::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return CartResponse.builder()
                .cartId(cart.getId())
                .totalAmount(total)
                .items(items)
                .build();
    }

    @Override
    public CartResponse updateQuantity(UpdateCartRequest request) {

        CartItem cartItem = cartItemRepository.findById(request.getCartItemId())
                .orElseThrow(() -> new ResourceNotFoundException("Cart Item not found"));

        if (request.getQuantity() <= 0) {

            cartItemRepository.delete(cartItem);

        } else {

            cartItem.setQuantity(request.getQuantity());

            cartItemRepository.save(cartItem);
        }

        return getCart();
    }

    @Override
    public void removeItem(Long cartItemId) {

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart Item not found"));

        cartItemRepository.delete(cartItem);
    }

    @Override
    public void clearCart() {

        User user = userService.getCurrentUser();

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        cart.getItems().clear();

        cartRepository.save(cart);
    }
}