package com.os.product_service.mapper;

import com.os.product_service.dto.request.product.CreateProductRequest;
import com.os.product_service.dto.request.product.UpdateProductRequest;
import com.os.product_service.dto.response.product.GetAllProductResponse;
import com.os.product_service.dto.response.product.GetByIdProductResponse;
import com.os.product_service.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductMapping {

        ProductMapping INSTANCE = Mappers.getMapper(ProductMapping.class);

        @Mapping(source = "categoryId",target = "category.id")
        Product createProduct(CreateProductRequest request);
        Product updateProduct(UpdateProductRequest request, @MappingTarget Product product);
        @Mapping(source = "category.id",target = "categoryId")
        GetByIdProductResponse getByIdProduct(Product product);

        @Mapping(source = "category.id",target = "categoryId")
        GetAllProductResponse getAllProduct(Product product);
        List<GetAllProductResponse> listGetAllProduct(List<Product> products);

}


