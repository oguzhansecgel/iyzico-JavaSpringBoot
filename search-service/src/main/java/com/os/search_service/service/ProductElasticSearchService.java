package com.os.search_service.service;

import com.os.search_service.document.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductElasticSearchService {
    Product saveProduct(Product product);
    Iterable<Product> getAllProducts();
    List<Product> searchProductByName(String productName);
    List<Product> findByProductPriceBetween(BigDecimal low, BigDecimal high);
    void deleteProduct(String id);
    Product updateProduct(Product product);
}
