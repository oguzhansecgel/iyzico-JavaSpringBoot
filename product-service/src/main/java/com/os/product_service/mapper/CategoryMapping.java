package com.os.product_service.mapper;

import com.os.product_service.dto.request.category.CreateCategoryRequest;
import com.os.product_service.dto.request.category.UpdateCategoryRequest;
import com.os.product_service.dto.response.category.GetAllCategoryResponse;
import com.os.product_service.dto.response.category.GetByIdCategoryResponse;
import com.os.product_service.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CategoryMapping {


    CategoryMapping INSTANCE = Mappers.getMapper(CategoryMapping.class);

    Category createCategory(CreateCategoryRequest request);
    Category updateCategory(UpdateCategoryRequest request,@MappingTarget Category category);
    GetByIdCategoryResponse getByIdCategory(Category category);

    GetAllCategoryResponse getAllCategory(Category category);
    List<GetAllCategoryResponse> listToGetAllCategory(List<Category> categoryList);


}
