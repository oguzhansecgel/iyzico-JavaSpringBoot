package com.os.search_service.controller;

import com.os.search_service.document.Product;
import com.os.search_service.service.ProductElasticSearchService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/productSearchService")
public class ProductElasticSearchController {

    private final ProductElasticSearchService productElasticSearchService;

    public ProductElasticSearchController(ProductElasticSearchService productElasticSearchService) {
        this.productElasticSearchService = productElasticSearchService;
    }
    @GetMapping("/getAll/products")
    public Iterable<Product> getAllProducts()
    {
        return productElasticSearchService.getAllProducts();
    }
    @GetMapping("/searchBy/productName")
    public List<Product> searchByProductName(@RequestParam String productName)
    {
        return productElasticSearchService.searchProductByName(productName);
    }
    @GetMapping("/findBy/priceBetween/{lowPrice}/{highPrice}")
    public List<Product> productPriceBetween(@PathVariable BigDecimal lowPrice, @PathVariable BigDecimal highPrice)
    {
        return productElasticSearchService.findByProductPriceBetween(lowPrice, highPrice);
    }
    @GetMapping("/findBy/productPriceAsc")
    public List<Product> findAllByProductByPriceAsc()
    {
        return productElasticSearchService.findAllByProductByPriceAsc();
    }
    @GetMapping("/findBy/productPriceDesc")
    public List<Product> findAllByProductByPriceDesc()
    {
        return productElasticSearchService.findAllByProductByPriceDesc();
    }
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id)
    {
        productElasticSearchService.deleteProduct(id);
    }
}
