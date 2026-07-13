package com.example.E_Commerce.dto.order;

import lombok.Data;

@Data
public class OrderRequest {

    private String name;

    private String email;

    private String phone;

    private String shippingAddress;

}