package com.os.product_service.service;

import com.os.product_service.dto.request.product.CreateProductRequest;
import com.os.product_service.dto.request.product.UpdateProductRequest;
import com.os.product_service.dto.response.product.CreateProductResponse;
import com.os.product_service.dto.response.product.GetAllProductResponse;
import com.os.product_service.dto.response.product.GetByIdProductResponse;
import com.os.product_service.dto.response.product.UpdateProductResponse;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    CreateProductResponse addProduct(CreateProductRequest request);
    UpdateProductResponse updateProduct(Long id, UpdateProductRequest request);
    List<GetAllProductResponse> getAllProducts();
    Optional<GetByIdProductResponse> getProductById(Long id);
    void deleteProduct(Long id);
    List<GetAllProductResponse> findByProductWithCategoryId(Long categoryId);
}
