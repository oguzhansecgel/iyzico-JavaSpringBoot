package com.os.product_service.service;

import com.os.product_service.dto.request.category.CreateCategoryRequest;
import com.os.product_service.dto.request.category.UpdateCategoryRequest;
import com.os.product_service.dto.response.category.CreateCategoryResponse;
import com.os.product_service.dto.response.category.GetAllCategoryResponse;
import com.os.product_service.dto.response.category.GetByIdCategoryResponse;
import com.os.product_service.dto.response.category.UpdateCategoryResponse;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    CreateCategoryResponse createCategory(CreateCategoryRequest request);
    UpdateCategoryResponse updateCategory(Long id,UpdateCategoryRequest request);
    List<GetAllCategoryResponse> getAllCategories();
    Optional<GetByIdCategoryResponse> getCategory(Long id);
    void deleteCategory(Long id);
}
