package com.danmag.ecommerce.service.service;

import com.danmag.ecommerce.service.dto.FeatureDTO;
import com.danmag.ecommerce.service.dto.ProductDTO;
import com.danmag.ecommerce.service.exceptions.ResourceNotFoundException;
import com.danmag.ecommerce.service.mapper.ProductMapper;
import com.danmag.ecommerce.service.model.Feature;
import com.danmag.ecommerce.service.model.FeatureValue;
import com.danmag.ecommerce.service.model.ProductFeatures;
import com.danmag.ecommerce.service.repository.FeatureRepository;
import com.danmag.ecommerce.service.repository.FeatureValueRepository;
import com.danmag.ecommerce.service.repository.ProductFeatureRepository;
import com.danmag.ecommerce.service.repository.ProductRepository;
import com.danmag.ecommerce.service.model.Product;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
//TODO Factory: You could use the Factory pattern to create different types of products in the online shop.
// The factory would have a method that takes in the type of product and returns an instance of that product
// without specifying the exact class. This allows for easy addition of new products to the shop without modifying the existing code.
//TODO Adapter: You could use the Adapter pattern to adapt the interface of the Payment Service
// to match the interface required by the shop.
// This allows the shop to use the Payment Service without having to modify the Payment Service code.

@Service
public class ProductService {
    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    private final ProductFeatureRepository productFeatureRepository;

    private final FeatureRepository featureRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public ProductService(ProductRepository productRepository, FeatureValueRepository featureValueRepository, ProductMapper productMapper, ProductFeatureRepository productFeatureRepository, FeatureRepository featureRepository) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.productFeatureRepository = productFeatureRepository;
        this.featureRepository = featureRepository;
    }


    public List<ProductDTO> getAllProductsWithFeatures() {

        List<Product> products = productRepository.findAll();
        List<ProductDTO> productDTOs = new ArrayList<>();
        for (Product product : products) {
            productDTOs.add(productMapper.convertToDTO(product));
        }
        return productDTOs;
    }

    public ProductDTO getProductById(Long id) throws Exception {
        Product product = productRepository.findById(id).orElseThrow(() -> new ExpressionException("Product not found with id " + id));
        return productMapper.convertToDTO(product);
    }

    public ProductDTO updateProduct(long id, ProductDTO productDTO) throws Exception {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            product.setName(productDTO.getName());
            product.setPrice(productDTO.getPrice());
            product = productRepository.save(product);
            return new ProductDTO();
        } else {
            throw new Exception();
        }
    }

    public ProductDTO createProduct(ProductDTO productDTO) {
        // Validate the input ProductDTO
        //Validator.validate(productDTO);
        Product product = productMapper.convertToEntity(productDTO);
        productRepository.saveAndFlush(product);
        List<ProductFeatures> productFeaturesList = productDTO.getFeatures().stream()
                .map(featureDTO -> createProductFeature(featureDTO, product))
                .collect(Collectors.toList());
        productFeatureRepository.saveAll(productFeaturesList);
        return productMapper.convertToDTO(product);
    }

    private ProductFeatures createProductFeature(FeatureDTO featureDTO, Product product) {
        Feature feature = featureRepository.findById(featureDTO.getId()).orElseThrow(() -> new ResourceNotFoundException("Feature not found with id: " + featureDTO.getId()));
        FeatureValue featureValue = modelMapper.map(featureDTO.getFeatureValues(), FeatureValue.class);
        featureValue.setFeature(feature);
        ProductFeatures productFeatures = new ProductFeatures();
        productFeatures.setProduct(product);
        productFeatures.setFeatureValue(featureValue);
        productFeatures.setFeature(feature);
        return productFeatures;
    }

    public void deleteProduct(long id) {

        productRepository.deleteById(id);
    }


}
