package com.example.E_Commerce.service;

import com.example.E_Commerce.dto.product.ProductRequest;
import com.example.E_Commerce.dto.product.ProductResponse;

import java.util.List;

public interface ProductService {

    ProductResponse addProduct(ProductRequest request);

    ProductResponse updateProduct(Long id, ProductRequest request);

    void deleteProduct(Long id);

    ProductResponse getProductById(Long id);

    List<ProductResponse> getAllProducts();

    List<ProductResponse> searchProducts(String keyword);

    List<ProductResponse> getProductsByCategory(String categoryName);

}