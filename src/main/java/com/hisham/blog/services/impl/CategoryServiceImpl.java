package com.hisham.blog.services.impl;

import com.hisham.blog.domain.dtos.CategoryDto;
import com.hisham.blog.domain.entities.Category;
import com.hisham.blog.repositories.CategoryRepository;
import com.hisham.blog.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> listCategories() {
        return categoryRepository.findAllWithPostCount();
    }
    @Override
    @Transactional
    public Category createCategory(Category category){
        String category_name = category.getName();
        if(categoryRepository.existsByNameIgnoreCase(category_name)){
            throw new IllegalArgumentException("Category already exists with name: " + category_name);
        }
        return categoryRepository.save(category);
    }
}
