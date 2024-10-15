package com.os.product_service.controller;

import com.os.product_service.dto.request.product.CreateProductRequest;
import com.os.product_service.dto.request.product.UpdateProductRequest;
import com.os.product_service.dto.response.product.CreateProductResponse;
import com.os.product_service.dto.response.product.GetAllProductResponse;
import com.os.product_service.dto.response.product.GetByIdProductResponse;
import com.os.product_service.dto.response.product.UpdateProductResponse;
import com.os.product_service.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/getAllProducts")
    public List<GetAllProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

    // Belirli bir ürünü ID ile alma
    @GetMapping("/getById/product/{id}")
    public Optional<GetByIdProductResponse> getProductById(@PathVariable Long id) {
       return productService.getProductById(id);
    }

    // Ürün silme
    @DeleteMapping("/delete/product/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PostMapping("/create/product")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateProductResponse addProduct(@RequestBody CreateProductRequest request) {
        return productService.addProduct(request);
    }

    @PutMapping("/update/product/{id}")
    public UpdateProductResponse updateProduct(@PathVariable Long id, @RequestBody UpdateProductRequest request) {
         return productService.updateProduct(id, request);
    }

}
