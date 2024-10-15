package com.os.product_service.service.impl;

import com.os.product_service.dto.request.product.CreateProductRequest;
import com.os.product_service.dto.request.product.UpdateProductRequest;
import com.os.product_service.dto.response.product.CreateProductResponse;
import com.os.product_service.dto.response.product.GetAllProductResponse;
import com.os.product_service.dto.response.product.GetByIdProductResponse;
import com.os.product_service.dto.response.product.UpdateProductResponse;
import com.os.product_service.mapper.ProductMapping;
import com.os.product_service.model.Product;
import com.os.product_service.repository.ProductRepository;
import com.os.product_service.service.ProductService;
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
    public CreateProductResponse addProduct(CreateProductRequest request) {
        Product product = ProductMapping.INSTANCE.createProduct(request);
        Product savedProduct = productRepository.save(product);
        return new CreateProductResponse(savedProduct.getId(),savedProduct.getName(),savedProduct.getDescription(),savedProduct.getPrice(),savedProduct.getCategory().getId());
    }

    @Override
    public UpdateProductResponse updateProduct(Long id, UpdateProductRequest request) {
        Optional<Product> product = productRepository.findById(id);
        // if (product.isEmpty()) {}  exception tanımlaması yapılacak
        Product existingProduct = product.get();
        Product updatedProduct = ProductMapping.INSTANCE.updateProduct(request, existingProduct);
        Product savedProduct =productRepository.save(updatedProduct);
        return new UpdateProductResponse(savedProduct.getId(),savedProduct.getName(),savedProduct.getDescription(),savedProduct.getPrice(),savedProduct.getCategory().getId());
    }

    @Override
    public List<GetAllProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return ProductMapping.INSTANCE.listGetAllProduct(products);
    }

    @Override
    public Optional<GetByIdProductResponse> getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.map(ProductMapping.INSTANCE::getByIdProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
