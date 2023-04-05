package com.danmag.ecommerce.service.service;

import com.danmag.ecommerce.service.dto.ProductDTO;
import com.danmag.ecommerce.service.dto.ProductFeatureDTO;
import com.danmag.ecommerce.service.dto.request.UpdateProductRequest;
import com.danmag.ecommerce.service.model.Feature;
import com.danmag.ecommerce.service.model.FeatureValue;
import com.danmag.ecommerce.service.model.Product;
import com.danmag.ecommerce.service.model.ProductFeatures;
import com.danmag.ecommerce.service.repository.FeatureValueRepository;
import com.danmag.ecommerce.service.repository.ProductFeatureRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductFeaturesService {
    private final ModelMapper modelMapper;
    private final ProductFeatureRepository productFeatureRepository;

    private final FeatureValueRepository featureValueRepository;

    public ProductFeaturesService(ModelMapper modelMapper, ProductFeatureRepository productFeatureRepository, FeatureValueRepository featureValueRepository) {
        this.modelMapper = modelMapper;
        this.productFeatureRepository = productFeatureRepository;
        this.featureValueRepository = featureValueRepository;
    }

    public List<ProductFeatureDTO> getProductFeatureListForProduct(long productId) {
        List<ProductFeatures> productFeaturesList = productFeatureRepository.findByProductId(productId);
        return productFeaturesList.stream()
                .map(productFeatures -> modelMapper.map(productFeatures, ProductFeatureDTO.class))
                .toList();
    }

    public void createProductFeatures(Product product, ProductDTO productDTO) {
        List<ProductFeatures> productFeaturesList = new ArrayList<>();
        for (ProductFeatureDTO productFeatureDTO : productDTO.getFeatures()) {
            Feature feature = modelMapper.map(productFeatureDTO.getFeature(), Feature.class);
            FeatureValue featureValue = modelMapper.map(productFeatureDTO.getFeatureValue(), FeatureValue.class);

            featureValue = getOrCreateFeatureValue(featureValue, feature);

            ProductFeatures productFeatures = new ProductFeatures();
            productFeatures.setProduct(product);
            productFeatures.setFeature(feature);
            productFeatures.setFeatureValue(featureValue);
            productFeaturesList.add(productFeatures);
        }

        productFeatureRepository.saveAll(productFeaturesList);
    }

    public void updateProductFeatures(long productId, UpdateProductRequest request) {
        List<ProductFeatures> savedProductFeatures = productFeatureRepository.findByProductId(productId);

        for (ProductFeatureDTO productFeatureDTO : request.getFeatures()) {
            Feature feature = modelMapper.map(productFeatureDTO.getFeature(), Feature.class);
            FeatureValue newFeatureValue = modelMapper.map(productFeatureDTO.getFeatureValue(), FeatureValue.class);

            newFeatureValue = getOrCreateFeatureValue(newFeatureValue, feature);

            // Find the existing ProductFeatures record for this feature
            ProductFeatures productFeatures = savedProductFeatures.stream()
                    .filter(pf -> pf.getFeature().getId()==(feature.getId()))
                    .findFirst()
                    .orElse(null);

            if (productFeatures != null) {
                // If an existing ProductFeatures record was found, update it with the new FeatureValue
                productFeatures.setFeatureValue(newFeatureValue);
                productFeatureRepository.save(productFeatures);
            } else {
                // If an existing ProductFeatures record wasn't found, create a new one with the new FeatureValue
                ProductFeatures newProductFeatures = new ProductFeatures();
                newProductFeatures.setProduct(modelMapper.map(request, Product.class));
                newProductFeatures.setFeature(feature);
                newProductFeatures.setFeatureValue(newFeatureValue);
                productFeatureRepository.save(newProductFeatures);
            }
        }
    }

    private FeatureValue getOrCreateFeatureValue(FeatureValue featureValue, Feature feature) {
        FeatureValue existingFeatureValue = featureValueRepository.findByfullName(featureValue.getFullName());

        if (existingFeatureValue != null) {
            // If it does, use the existing FeatureValue
            featureValue = existingFeatureValue;
        } else {
            // If it doesn't, save the new FeatureValue
            featureValue.setFeature(feature);
            featureValueRepository.save(featureValue);
        }

        return featureValue;
    }



}


