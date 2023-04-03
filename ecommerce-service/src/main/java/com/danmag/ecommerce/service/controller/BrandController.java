package com.danmag.ecommerce.service.controller;

import com.danmag.ecommerce.service.model.Brand;
import com.danmag.ecommerce.service.service.BrandService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin

@RestController
@RequestMapping(value = "/api")
public class BrandController {
    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping(value = "/brand")
    public List<Brand> getBrand() {
        return brandService.getAllBrands();
    }
}
