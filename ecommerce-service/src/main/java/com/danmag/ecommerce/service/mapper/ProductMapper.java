package com.danmag.ecommerce.service.mapper;

import com.danmag.ecommerce.service.dto.*;
import com.danmag.ecommerce.service.model.*;
import com.danmag.ecommerce.service.repository.FeatureRepository;
import com.danmag.ecommerce.service.repository.FeatureValueRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ProductMapper {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private FeatureRepository featureRepository;
    @Autowired
    private FeatureValueRepository featureValueRepository;


    public ProductDTO convertToDTO(Product product) {
        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
        productDTO.setBrand(modelMapper.map(product.getBrand(), BrandDTO.class));
        productDTO.setCategory(modelMapper.map(product.getCategory(), CategoryDTO.class));
        List<FeatureDTO> featureDTOs = product.getCategory().getFeature().stream().map(feature -> {
            Optional<ProductFeatures> optionalProductFeatures = product.getFeatures().stream().filter(pf -> pf.getFeature().equals(feature)).findFirst();
            return optionalProductFeatures.map(pf -> {
                FeatureDTO featureDTO = modelMapper.map(feature, FeatureDTO.class);
                FeatureValueDTO featureValueDTO = modelMapper.map(pf.getFeatureValue(), FeatureValueDTO.class);
                featureDTO.setFeatureValues(featureValueDTO);
                return featureDTO;
            }).orElse(null);
        }).filter(Objects::nonNull).collect(Collectors.toList());
        productDTO.setFeatures(featureDTOs);
        return productDTO;
    }

    public Product convertToEntity(ProductDTO productDTO) {
        Product product = modelMapper.map(productDTO, Product.class);
        product.setBrand(modelMapper.map(productDTO.getBrand(), Brand.class));
        product.setCategory(modelMapper.map(productDTO.getCategory(), Category.class));
        List<ProductFeatures> features = productDTO.getFeatures().stream().map(featureDTO -> {
            Feature feature = modelMapper.map(featureDTO, Feature.class);
            FeatureValue featureValue = modelMapper.map(featureDTO.getFeatureValues(), FeatureValue.class);
            ProductFeatures productFeatures = new ProductFeatures();
            productFeatures.setFeature(feature);
            productFeatures.setFeatureValue(featureValue);
            productFeatures.setProduct(product);
            return productFeatures;
        }).collect(Collectors.toList());
        product.setFeatures(features);
        return product;
    }

}