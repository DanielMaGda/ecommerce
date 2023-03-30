package com.danmag.ecommerce.service.service;

import com.danmag.ecommerce.service.dto.CategoryDTO;
import com.danmag.ecommerce.service.model.Category;
import com.danmag.ecommerce.service.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    private ModelMapper modelMapper;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDTO> getCategory() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        for (Category category : categories) {
            categoryDTOS.add(modelMapper.map(category, CategoryDTO.class));
        }
        return categoryDTOS;
    }
}
