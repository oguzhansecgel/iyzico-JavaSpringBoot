package com.os.search_service.service.impl;

import com.os.search_service.document.Product;
import com.os.search_service.repository.ProductElasticSearchRepository;
import com.os.search_service.service.ProductElasticSearchService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductElasticSearchServiceImpl implements ProductElasticSearchService {

    private final ProductElasticSearchRepository productElasticSearchRepository;

    public ProductElasticSearchServiceImpl(ProductElasticSearchRepository productElasticSearchRepository) {
        this.productElasticSearchRepository = productElasticSearchRepository;
    }

    @Override
    public Product saveProduct(Product product) {
        return productElasticSearchRepository.save(product);
    }

    @Override
    public Iterable<Product> getAllProducts() {
        return productElasticSearchRepository.findAll();
    }

    @Override
    public List<Product> searchProductByName(String productName) {
        return productElasticSearchRepository.searchByProductName(productName);
    }

    @Override
    public List<Product> findByProductPriceBetween(BigDecimal low, BigDecimal high) {
        return productElasticSearchRepository.findByPriceBetween(low,high);
    }

    @Override
    public void deleteProduct(String id) {
        productElasticSearchRepository.deleteById(id);
    }

    @Override
    public Product updateProduct(Product product) {
        Optional<Product> optionalProduct = productElasticSearchRepository.findById(product.getId().toString());
        Product existingProduct = optionalProduct.get();

        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setCategoryId(product.getCategoryId());

        return productElasticSearchRepository.save(existingProduct);
    }

    @Override
    public List<Product> findAllByProductByPriceAsc() {
        return productElasticSearchRepository.findAllByOrderByPriceAsc();
    }

    @Override
    public List<Product> findAllByProductByPriceDesc() {
        return productElasticSearchRepository.findAllByOrderByPriceDesc();
    }


}
