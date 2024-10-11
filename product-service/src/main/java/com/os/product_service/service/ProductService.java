package com.os.product_service.service;

import com.os.product_service.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Product addProduct(Product product);
    Product updateProduct(int id, Product updatedProduct);
    List<Product> getAllProducts();
    Optional<Product> getProductById(int id);
    void deleteProduct(int id);
}
