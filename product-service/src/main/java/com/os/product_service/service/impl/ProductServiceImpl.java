package com.os.product_service.service.impl;

import com.os.product_service.dto.request.product.CreateProductRequest;
import com.os.product_service.dto.request.product.DeleteProductRequest;
import com.os.product_service.dto.request.product.UpdateProductRequest;
import com.os.product_service.dto.response.product.CreateProductResponse;
import com.os.product_service.dto.response.product.GetAllProductResponse;
import com.os.product_service.dto.response.product.GetByIdProductResponse;
import com.os.product_service.dto.response.product.UpdateProductResponse;
import com.os.product_service.exception.type.ProductNotFoundException;
import com.os.product_service.kafka.SearchServiceProducer;
import com.os.product_service.mapper.ProductMapping;
import com.os.product_service.model.Product;
import com.os.product_service.repository.ProductRepository;
import com.os.product_service.service.ProductService;
import com.os.product_service.utils.message.ProductMessage;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {


    private final ProductRepository productRepository;
    private final SearchServiceProducer searchServiceProducer;
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    public ProductServiceImpl(ProductRepository productRepository, SearchServiceProducer searchServiceProducer) {
        this.productRepository = productRepository;
        this.searchServiceProducer = searchServiceProducer;
    }

    @Override
    @CacheEvict(value = "products", key = "'allProducts'")
    public CreateProductResponse addProduct(CreateProductRequest request) {
        Product product = ProductMapping.INSTANCE.createProduct(request);
        Product savedProduct = productRepository.save(product);
        logger.info("Product Created : {}", savedProduct);
        searchServiceProducer.sendMessage(new CreateProductResponse(savedProduct.getId(),savedProduct.getName(),savedProduct.getDescription(),savedProduct.getPrice(),savedProduct.getCategory().getId()));
        return new CreateProductResponse(savedProduct.getId(),savedProduct.getName(),savedProduct.getDescription(),savedProduct.getPrice(),savedProduct.getCategory().getId());
    }

    @Override
    @CacheEvict(value = "products", key = "'allProducts'")
    public UpdateProductResponse updateProduct(Long id, UpdateProductRequest request) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty())
        {
            throw new ProductNotFoundException(ProductMessage.PRODUCT_NOT_FOUND);
        }
        Product existingProduct = product.get();
        Product updatedProduct = ProductMapping.INSTANCE.updateProduct(request, existingProduct);
        Product savedProduct =productRepository.save(updatedProduct);
        logger.info("Product Saved : {}", savedProduct);
        searchServiceProducer.updateSendMessage(new UpdateProductResponse(savedProduct.getId(),savedProduct.getName(),savedProduct.getDescription(),savedProduct.getPrice(),savedProduct.getCategory().getId()));
        return new UpdateProductResponse(savedProduct.getId(),savedProduct.getName(),savedProduct.getDescription(),savedProduct.getPrice(),savedProduct.getCategory().getId());
    }

    @Override
    @Cacheable(value = "products", key = "'allProducts'")
    public List<GetAllProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return ProductMapping.INSTANCE.listGetAllProduct(products);
    }

    @Override
    @Cacheable(value = "products", key = "#id")
    public Optional<GetByIdProductResponse> getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty())
        {
            logger.error(ProductMessage.PRODUCT_NOT_FOUND);
            throw new ProductNotFoundException(ProductMessage.PRODUCT_NOT_FOUND);
        }
        return product.map(ProductMapping.INSTANCE::getByIdProduct);
    }

    @Override
    @CacheEvict(value = "products", key = "#id",allEntries = true)
    public void deleteProduct(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty())
        {
            logger.error(ProductMessage.PRODUCT_NOT_FOUND);
            throw new ProductNotFoundException(ProductMessage.PRODUCT_NOT_FOUND);
        }
        DeleteProductRequest request = new DeleteProductRequest();
        request.setId(id);
        searchServiceProducer.deleteSendMessage(request);
        productRepository.deleteById(id);
    }

    @Override
    @CacheEvict(value = "products", key = "#categoryId",allEntries = true)
    public List<GetAllProductResponse> findByProductWithCategoryId(Long categoryId) {
        List<Product> products = productRepository.findByCategoryId(categoryId);
        return ProductMapping.INSTANCE.listGetAllProduct(products);
    }
}
