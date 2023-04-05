package com.danmag.ecommerce.service.controller;

import com.danmag.ecommerce.service.api.ApiResponse;
import com.danmag.ecommerce.service.dto.CategoryDTO;
import com.danmag.ecommerce.service.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin

@RestController
@RequestMapping(value = "/api/v1")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(value = "/category")
    public ResponseEntity<ApiResponse<List<CategoryDTO>>> getCategory() {
        try {
            List<CategoryDTO> categories = categoryService.getCategory();
            ApiResponse<List<CategoryDTO>> response = new ApiResponse<>(HttpStatus.OK.value(), "Success", categories);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
