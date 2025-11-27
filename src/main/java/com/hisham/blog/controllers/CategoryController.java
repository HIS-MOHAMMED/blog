package com.hisham.blog.controllers;


import com.hisham.blog.domain.dtos.CategoryDto;
import com.hisham.blog.domain.entities.Category;
import com.hisham.blog.mappers.CategoryMapper;
import com.hisham.blog.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path="/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> listCategories(){
       List<CategoryDto> categories =  categoryService.listCategories()
               .stream().map(categoryMapper::toDto).toList();
       return ResponseEntity.ok(categories);
    }
}
