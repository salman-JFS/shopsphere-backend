package com.example.E_Commerce.dto.user;

import lombok.Data;

@Data
public class UpdateProfileRequest {

    private String name;

    private String phone;

    private String address;

}