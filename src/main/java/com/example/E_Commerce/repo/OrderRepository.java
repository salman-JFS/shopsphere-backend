package com.example.E_Commerce.repo;

import com.example.E_Commerce.model.Orders;
import com.example.E_Commerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {

    List<Orders> findByUser(User user);

}