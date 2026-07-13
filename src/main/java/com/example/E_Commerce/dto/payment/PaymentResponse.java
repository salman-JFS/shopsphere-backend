package com.example.E_Commerce.dto.payment;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentResponse {

    private String key;

    private String razorpayOrderId;

    private Long amount;

    private String currency;

    private String status;
}