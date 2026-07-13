package com.example.E_Commerce.service;

import com.example.E_Commerce.dto.payment.PaymentResponse;

public interface PaymentService {

    PaymentResponse createPayment(Long orderId);

}