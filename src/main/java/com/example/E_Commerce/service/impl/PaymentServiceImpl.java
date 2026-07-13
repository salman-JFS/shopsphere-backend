package com.example.E_Commerce.service.impl;

import com.example.E_Commerce.dto.payment.PaymentResponse;
import com.example.E_Commerce.exception.ResourceNotFoundException;
import com.example.E_Commerce.model.Orders;
import com.example.E_Commerce.model.Payment;
import com.example.E_Commerce.model.PaymentStatus;
import com.example.E_Commerce.repo.OrderRepository;
import com.example.E_Commerce.repo.PaymentRepository;
import com.example.E_Commerce.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {


    @Value("${razorpay.key}")
    private String razorpayKey;

    @Value("${razorpay.secret}")
    private String razorpaySecret;

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    @Override
    public PaymentResponse createPayment(Long orderId) {

        try {

            Orders order = orderRepository.findById(orderId)
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Order not found"));

            RazorpayClient razorpay =
                    new RazorpayClient(razorpayKey, razorpaySecret);

            JSONObject options = new JSONObject();

            options.put(
                    "amount",
                    order.getTotalAmount()
                            .multiply(BigDecimal.valueOf(100))
                            .longValue()
            );

            options.put("currency", "INR");
            options.put("receipt", "order_" + order.getId());

            Order razorpayOrder = razorpay.orders.create(options);

            Payment payment = Payment.builder()
                    .order(order)
                    .amount(order.getTotalAmount())
                    .razorpayOrderId(
                            razorpayOrder.get("id")
                    )
                    .status(PaymentStatus.CREATED)
                    .build();

            paymentRepository.save(payment);

            return PaymentResponse.builder()
                    .key(razorpayKey)
                    .razorpayOrderId(
                            razorpayOrder.get("id")
                    )
                    .amount(
                            order.getTotalAmount()
                                    .multiply(BigDecimal.valueOf(100))
                                    .longValue()
                    )
                    .currency("INR")
                    .status(payment.getStatus().name())
                    .build();

        } catch (Exception e) {

            throw new RuntimeException(e.getMessage());

        }
    }
}