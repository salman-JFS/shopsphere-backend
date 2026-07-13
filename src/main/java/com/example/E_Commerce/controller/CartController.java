package com.example.E_Commerce.controller;

import com.example.E_Commerce.dto.cart.AddToCartRequest;
import com.example.E_Commerce.dto.cart.CartResponse;
import com.example.E_Commerce.dto.cart.UpdateCartRequest;
import com.example.E_Commerce.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    public CartResponse addToCart(@RequestBody AddToCartRequest request) {
        return cartService.addToCart(request);
    }

    @GetMapping
    public CartResponse getCart() {
        return cartService.getCart();
    }

    @PutMapping("/update")
    public CartResponse updateQuantity(
            @RequestBody UpdateCartRequest request) {

        return cartService.updateQuantity(request);
    }

    @DeleteMapping("/remove/{cartItemId}")
    public String removeItem(@PathVariable Long cartItemId) {
        cartService.removeItem(cartItemId);
        return "Item Removed Successfully";
    }

    @DeleteMapping("/clear")
    public String clearCart() {
        cartService.clearCart();
        return "Cart Cleared Successfully";
    }
}