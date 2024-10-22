package com.os.product_service.service.impl;

import com.os.product_service.dto.request.category.CreateCategoryRequest;
import com.os.product_service.dto.request.category.UpdateCategoryRequest;
import com.os.product_service.dto.response.category.CreateCategoryResponse;
import com.os.product_service.dto.response.category.GetAllCategoryResponse;
import com.os.product_service.dto.response.category.GetByIdCategoryResponse;
import com.os.product_service.dto.response.category.UpdateCategoryResponse;
import com.os.product_service.exception.type.CategoryNotFoundException;
import com.os.product_service.mapper.CategoryMapping;
import com.os.product_service.model.Category;
import com.os.product_service.repository.CategoryRepository;
import com.os.product_service.service.CategoryService;
import com.os.product_service.utils.message.CategoryMessage;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @CacheEvict(value = "category",key = "'getAll'")
    public CreateCategoryResponse createCategory(CreateCategoryRequest request) {
        Category category = CategoryMapping.INSTANCE.createCategory(request);
        Category savedCategory = categoryRepository.save(category);
        return new CreateCategoryResponse(savedCategory.getId(),savedCategory.getName());
    }

    @Override
    @CacheEvict(value = "category",key = "'getAll'")
    public UpdateCategoryResponse updateCategory(Long id, UpdateCategoryRequest request) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty())
        {
            throw new CategoryNotFoundException(CategoryMessage.CATEGORY_NOT_FOUND);
        }
        Category categoryExisting = optionalCategory.get();
        Category category = CategoryMapping.INSTANCE.updateCategory(request,categoryExisting);
        Category savedCategory = categoryRepository.save(category);
        return new UpdateCategoryResponse(savedCategory.getId(),savedCategory.getName());
    }

    @Override
    @Cacheable(value = "category",key = "'getAll'")
    public List<GetAllCategoryResponse> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return CategoryMapping.INSTANCE.listToGetAllCategory(categories);
    }

    @Override
    @Cacheable(value = "category",key = "#id")
    public Optional<GetByIdCategoryResponse> getCategory(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty())
        {
            throw new CategoryNotFoundException(CategoryMessage.CATEGORY_NOT_FOUND);
        }
        return optionalCategory.map(CategoryMapping.INSTANCE::getByIdCategory);
    }

    @Override
    @CacheEvict(value = "category",key = "#id",allEntries = true)
    public void deleteCategory(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty())
        {
            throw new CategoryNotFoundException(CategoryMessage.CATEGORY_NOT_FOUND);
        }
        categoryRepository.deleteById(id);
    }


}
