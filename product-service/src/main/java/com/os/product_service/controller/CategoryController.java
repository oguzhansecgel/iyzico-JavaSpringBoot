package com.os.product_service.controller;

import com.os.product_service.dto.request.category.CreateCategoryRequest;
import com.os.product_service.dto.request.category.UpdateCategoryRequest;
import com.os.product_service.dto.response.category.CreateCategoryResponse;
import com.os.product_service.dto.response.category.GetAllCategoryResponse;
import com.os.product_service.dto.response.category.GetByIdCategoryResponse;
import com.os.product_service.dto.response.category.UpdateCategoryResponse;
import com.os.product_service.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/getById/category/{categoryId}")
    public Optional<GetByIdCategoryResponse> getById(@PathVariable Long categoryId)
    {
        return categoryService.getCategory(categoryId);
    }
    @GetMapping("/listOf/category")
    public List<GetAllCategoryResponse> listOfCategory()
    {
        return categoryService.getAllCategories();
    }
    @DeleteMapping("/delete/category/{categoryId}")
    public void deleteCategory(@PathVariable Long categoryId)
    {
        categoryService.deleteCategory(categoryId);
    }
    @PostMapping("/create/category")
    public CreateCategoryResponse createCategory(@RequestBody CreateCategoryRequest createCategoryRequest)
    {
        return categoryService.createCategory(createCategoryRequest);
    }
    @PutMapping("/update/category/{categoryId}")
    public UpdateCategoryResponse updateCategory(@PathVariable Long categoryId, @RequestBody UpdateCategoryRequest updateCategoryRequest)
    {
        return categoryService.updateCategory(categoryId,updateCategoryRequest);
    }
}
