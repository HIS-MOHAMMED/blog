package com.hisham.blog.services;

import com.hisham.blog.domain.entities.Category;
import com.hisham.blog.repositories.CategoryRepository;
import com.hisham.blog.services.impl.CategoryServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTests {

    @Mock
    private  CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private Category category1;
    private Category category2;

    @BeforeEach
    public void init(){
        category1 = Category.builder()
                .name("Science")
                .build();
        category2 = Category.builder()
                .name("Medical")
                .build();
    }

    @Test
    public void givenExistingCategories_whenListCategories_thenReturnAll(){
        List<Category> categories = new ArrayList<>();
        categories.add(category1);
        categories.add(category2);

        when(categoryRepository.findAllWithPostCount()).thenReturn(categories);
        List<Category> returnedCategories = categoryService.listCategories();

        Assertions.assertThat(returnedCategories).isNotNull();
        Assertions.assertThat(returnedCategories.size()).isEqualTo(2);
        Assertions.assertThat(returnedCategories)
                .extracting(Category::getName)
                .containsExactly("Science","Medical");
        verify(categoryRepository,times(1)).findAllWithPostCount();
    }

    @Test
    public void givenCategory_whenCreateCategory_thenReturnSavedCategory(){
        // Act
        when(categoryRepository.existsByNameIgnoreCase(any(String.class))).thenReturn(false);
        when(categoryRepository.save(any(Category.class))).thenReturn(category1);
        Category savedCategory = categoryService.createCategory(category1);

        // Assert
        Assertions.assertThat(savedCategory).isNotNull();
        Assertions.assertThat(savedCategory.getName()).isEqualTo("Science");
        verify(categoryRepository, times(1)).existsByNameIgnoreCase(any(String.class));
    }
}
