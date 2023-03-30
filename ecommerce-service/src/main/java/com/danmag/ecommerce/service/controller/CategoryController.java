package com.danmag.ecommerce.service.controller;

import com.danmag.ecommerce.service.dto.CategoryDTO;
import com.danmag.ecommerce.service.service.CategoryService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin

@RestController
@RequestMapping(value = "/api")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(value = "/category")
    public List<CategoryDTO> getCategory() {
        return categoryService.getCategory();
    }
}
