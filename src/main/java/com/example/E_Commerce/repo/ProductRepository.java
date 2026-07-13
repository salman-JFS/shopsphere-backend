package com.example.E_Commerce.repo;

import com.example.E_Commerce.model.Category;
import com.example.E_Commerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategory(Category category);

    List<Product> findByCategory_CategoryName(String categoryName);

    List<Product> findByNameContainingIgnoreCase(String keyword);

}