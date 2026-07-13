package com.example.E_Commerce.dto.cart;

import lombok.Data;

@Data
public class AddToCartRequest {

    private Long productId;

    private Integer quantity;
}