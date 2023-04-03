package com.danmag.ecommerce.service.service;

import com.danmag.ecommerce.service.model.Brand;
import com.danmag.ecommerce.service.repository.BrandRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {
    private final BrandRepository brandRepository;

    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }
    //TODO make rest of Crud
    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }
}
