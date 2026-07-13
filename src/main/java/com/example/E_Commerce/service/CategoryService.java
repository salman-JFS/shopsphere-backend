package com.example.E_Commerce.service;

import com.example.E_Commerce.dto.product.ProductResponse;
import com.example.E_Commerce.model.Category;

import java.util.List;

public interface CategoryService {

    Category addCategory(Category category);

    Category updateCategory(Long id, Category category);

    void deleteCategory(Long id);

    List<Category> getAllCategories();

}