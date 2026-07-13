package com.example.E_Commerce.controller;

import com.example.E_Commerce.dto.payment.PaymentResponse;
import com.example.E_Commerce.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/{orderId}")
    public PaymentResponse createPayment(@PathVariable Long orderId) {
        return paymentService.createPayment(orderId);
    }
}