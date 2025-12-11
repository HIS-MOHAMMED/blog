package com.hisham.blog.services;

import com.hisham.blog.domain.dtos.CategoryDto;
import com.hisham.blog.domain.entities.Category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryService {
    List<Category> listCategories();
    Category createCategory(Category category);
    void deleteCategory(UUID uuid);
}
