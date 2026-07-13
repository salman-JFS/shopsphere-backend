package com.example.E_Commerce.controller;

import com.example.E_Commerce.dto.product.ProductRequest;
import com.example.E_Commerce.dto.product.ProductResponse;
import com.example.E_Commerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ProductResponse addProduct(@RequestBody ProductRequest request) {
        return productService.addProduct(request);
    }

    @PutMapping("/{id}")
    public ProductResponse updateProduct(@PathVariable Long id,
                                         @RequestBody ProductRequest request) {
        return productService.updateProduct(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "Product Deleted Successfully";
    }

    @GetMapping("/{id}")
    public ProductResponse getProduct(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @GetMapping
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/search")
    public List<ProductResponse> searchProduct(
            @RequestParam String keyword) {
        return productService.searchProducts(keyword);
    }
    @GetMapping("/category/{categoryName}")
    public List<ProductResponse> getProductsByCategory(
            @PathVariable String categoryName) {
        return productService.getProductsByCategory(categoryName);
    }
}