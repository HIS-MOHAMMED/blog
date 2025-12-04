package com.hisham.blog.controllers;
import com.hisham.blog.domain.dtos.CategoryDto;
import com.hisham.blog.domain.dtos.CreateCategoryRequest;
import com.hisham.blog.domain.entities.Category;
import com.hisham.blog.mappers.CategoryMapper;
import com.hisham.blog.services.impl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.sound.midi.MidiChannel;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import java.util.ArrayList;
import java.util.List;

@WebMvcTest(CategoryController.class)
@AutoConfigureMockMvc
public class CategoryControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CategoryServiceImpl categoryService;

    @MockitoBean
    private CategoryMapper categoryMapper;

    private Category category1;
    private Category category2;
    private CategoryDto categoryDto1;
    private CategoryDto categoryDto2;

    @BeforeEach
    public void init(){
        category1 = Category.builder()
                .name("Science")
                .build();
        category2 = Category.builder()
                .name("Medical")
                .build();
        categoryDto1 = CategoryDto.builder()
                .name("Science")
                .postCount(2)
                .build();
        categoryDto2 = CategoryDto.builder()
                .name("Medical")
                .postCount(1)
                .build();
    }
    @Test
    public void giveExistingCategories_whenListCategories_thenReturnResponseOfListOfDto() throws Exception{
        List<Category> categories = new ArrayList<>();
        categories.add(category1);
        categories.add(category2);

        given(categoryService.listCategories()).willReturn(categories);
        given(categoryMapper.toDto(any(Category.class))).willReturn(categoryDto1, categoryDto2);
        ResultActions resultActions = mockMvc.perform(get("/api/v1/categories")
                .accept(MediaType.APPLICATION_JSON));


        resultActions.andExpect(MockMvcResultMatchers
                .status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Science"))
                .andExpect(jsonPath("$[1].postCount").value(1));
    }
    @Test
    public void givenValidCreateRequest_whenCreateCategory_thenStatusCreated() throws Exception{
        given(categoryMapper.toEntity(any(CreateCategoryRequest.class))).willReturn(category1);
        given(categoryService.createCategory(any(Category.class))).willReturn(category1);
        given(categoryMapper.toDto(any(Category.class))).willReturn(categoryDto1);


        ResultActions resultActions = mockMvc.perform(post("/api/v1/categories")
                .accept(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "name": "Science"
                        }
                        """)
                .contentType(MediaType.APPLICATION_JSON));


        resultActions.andExpect(MockMvcResultMatchers
                .status().isCreated())
                .andExpect(jsonPath("$.name").value("Science"));

    }
}
