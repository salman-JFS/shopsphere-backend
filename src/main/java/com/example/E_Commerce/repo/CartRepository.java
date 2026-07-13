package com.example.E_Commerce.repo;

import com.example.E_Commerce.model.Cart;
import com.example.E_Commerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUser(User user);

}