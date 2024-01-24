package com.example.bidwise.service.category.impl;

import com.example.bidwise.entity.product.CategoryEntity;
import com.example.bidwise.repository.product.CategoryRepository;
import com.example.bidwise.service.category.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryEntity> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public List<CategoryEntity> findAllExcept(Long id) {
        return categoryRepository.findAll().stream().filter((category) -> !Objects.equals(category.getId(), id)).collect(Collectors.toList());
    }

    @Override
    public CategoryEntity findById(Long id) throws Exception {
        return categoryRepository.findById(id).orElseThrow(Exception::new);
    }

    @Override
    public void addCategory(String name, Long parentCategoryId) {
        if (parentCategoryId != null) {
            categoryRepository.save(new CategoryEntity(name, categoryRepository.findById(parentCategoryId).get()));
        } else {
            categoryRepository.save(new CategoryEntity(name));
        }
    }

    @Override
    public void editCategory(Long id, String name, Long parentCategoryId) throws Exception {
        CategoryEntity category = categoryRepository.findById(id).orElseThrow(Exception::new);
        if (parentCategoryId != null) {
            category.setName(name);
            category.setCategory(categoryRepository.findById(parentCategoryId).get());
            categoryRepository.save(category);
        } else {
            category.setName(name);
            category.setCategory(null);
            categoryRepository.save(category);
        }
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
