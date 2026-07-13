package com.example.E_Commerce.repo;

import com.example.E_Commerce.model.Payment;
import com.example.E_Commerce.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByOrder(Orders order);

    Optional<Payment> findByRazorpayOrderId(String razorpayOrderId);

}