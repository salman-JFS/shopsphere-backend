package com.example.E_Commerce.service;

public interface EmailService {

    void sendOrderConfirmation(String to, String orderId);

}