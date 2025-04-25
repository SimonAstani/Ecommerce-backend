package com.eCommerce.EcommerceBackend.service;

import com.eCommerce.EcommerceBackend.model.Category;
import com.eCommerce.EcommerceBackend.payload.CategoryDTO;
import com.eCommerce.EcommerceBackend.payload.CategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    CategoryDTO createCategory(CategoryDTO categoryDTO);


    CategoryDTO deleteCategory(Long categoryId);

    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId);

}
