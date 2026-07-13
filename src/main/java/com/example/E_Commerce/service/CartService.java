package com.example.E_Commerce.service;

import com.example.E_Commerce.dto.cart.AddToCartRequest;
import com.example.E_Commerce.dto.cart.CartResponse;
import com.example.E_Commerce.dto.cart.UpdateCartRequest;

public interface CartService {

    CartResponse addToCart(AddToCartRequest request);

    CartResponse getCart();

    CartResponse updateQuantity(UpdateCartRequest request);

    void removeItem(Long cartItemId);

    void clearCart();

}