package com.danmag.ecommerce.service.service;

import com.danmag.ecommerce.service.model.Category;
import com.danmag.ecommerce.service.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class CategoryServiceTest {
    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ModelMapper modelMapper;

    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        categoryService = new CategoryService(categoryRepository, modelMapper);
    }

    @Test
    void getCategory() {
        categoryService.getCategory();

        assertEquals(2, mockCategoryList().size());
        assertEquals("Phones", mockCategoryList().get(0).getName());
        assertEquals("Gaming Laptops", mockCategoryList().get(1).getName());


    }

    private List<Category> mockCategoryList() {
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(Category.builder()
                .id(1)
                .name("Phones")
                .build());
        categoryList.add(Category.builder()
                .id(2)
                .name("Gaming Laptops")
                .build());
        return categoryList;
    }
}