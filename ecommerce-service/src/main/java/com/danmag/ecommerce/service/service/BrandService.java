package com.danmag.ecommerce.service.service;

import com.danmag.ecommerce.service.dto.BrandDTO;
import com.danmag.ecommerce.service.model.Brand;
import com.danmag.ecommerce.service.repository.BrandRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {
    private final BrandRepository brandRepository;
    @Autowired
    private ModelMapper modelMapper;

    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public List<BrandDTO> getAllBrands() {

        List<Brand> brands = brandRepository.findAll();
        return brands.stream()
                .map(category -> modelMapper.map(brands, BrandDTO.class))
                .toList();
    }

    public BrandDTO addBrand(BrandDTO brandDTO) {
        Brand brand = modelMapper.map(brandDTO, Brand.class);
        brandRepository.save(brand);
        return modelMapper.map(brand, BrandDTO.class);

    }

}

