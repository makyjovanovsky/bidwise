package com.example.bidwise.service.category;

import com.example.bidwise.entity.product.CategoryEntity;

import java.util.List;

public interface CategoryService {

    List<CategoryEntity> findAll();

    List<CategoryEntity> findAllExcept(Long id);

    CategoryEntity findById(Long id) throws Exception;

    void addCategory(String name, Long parentCategoryId);

    void editCategory(Long id, String name, Long parentCategoryId) throws Exception;

    void deleteCategory(Long id);
}
