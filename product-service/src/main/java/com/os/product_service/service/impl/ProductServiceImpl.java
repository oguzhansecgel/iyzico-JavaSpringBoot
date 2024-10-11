package com.os.product_service.service.impl;

import com.os.product_service.model.Product;
import com.os.product_service.repository.ProductRepository;
import com.os.product_service.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {


    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(int id, Product updatedProduct) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent())
        {
            Product product = productOptional.get();
            product.setName(updatedProduct.getName());
            product.setPrice(updatedProduct.getPrice());
            product.setDescription(updatedProduct.getDescription());
            return productRepository.save(product);
        }
        return null;

    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> getProductById(int id) {
        Optional<Product> productOptional = productRepository.findById(id);
        return productOptional;
    }

    @Override
    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }
}
