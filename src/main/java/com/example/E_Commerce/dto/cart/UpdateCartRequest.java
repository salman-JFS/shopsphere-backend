package com.example.E_Commerce.dto.cart;

import lombok.Data;

@Data
public class UpdateCartRequest {

    private Long cartItemId;

    private Integer quantity;
}