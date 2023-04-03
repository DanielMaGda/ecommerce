package com.danmag.ecommerce.service.service;

import com.danmag.ecommerce.service.dto.ProductDTO;
import com.danmag.ecommerce.service.dto.ProductFeatureDTO;
import com.danmag.ecommerce.service.model.Product;
import com.danmag.ecommerce.service.repository.FeatureRepository;
import com.danmag.ecommerce.service.repository.ProductFeatureRepository;
import com.danmag.ecommerce.service.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
//TODO Factory: You could use the Factory pattern to create different types of products in the online shop.
// The factory would have a method that takes in the type of product and returns an instance of that product
// without specifying the exact class. This allows for easy addition of new products to the shop without modifying the existing code.
//TODO Adapter: You could use the Adapter pattern to adapt the interface of the Payment Service
// to match the interface required by the shop.
// This allows the shop to use the Payment Service without having to modify the Payment Service code.

@Service
public class ProductService {
    private final ProductRepository productRepository;

    private final ProductFeaturesService productFeaturesService;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductFeaturesService productFeaturesService) {
        this.productRepository = productRepository;

        this.productFeaturesService = productFeaturesService;
    }

    public List<ProductDTO> getAllProductsWithFeatures() {

        List<Product> productList = productRepository.findAll();
        return productList.stream()
                .map(product -> {
                    ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
                    List<ProductFeatureDTO> productFeatureDTO = productFeaturesService.getProductFeatureListForProduct(product.getId());
                    productDTO.setFeatures(productFeatureDTO);
                    return productDTO;
                })
                .toList();
    }

    public ProductDTO getProductById(long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ExpressionException("Product not found with id " + id));
        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
        List<ProductFeatureDTO> productFeatureDTO = productFeaturesService.getProductFeatureListForProduct(id);
        productDTO.setFeatures(productFeatureDTO);


        return productDTO;
    }

    public ProductDTO updateProduct(long id, ProductDTO productDTO) throws Exception {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            product.setName(productDTO.getName());
            product.setPrice(productDTO.getPrice());
            product = productRepository.saveAndFlush(product);
            productFeaturesService.updateProductFeatures(product.getId(), productDTO);
            return modelMapper.map(product, ProductDTO.class);
        } else {
            throw new Exception("Product not found with id " + id);
        }

    }

    public ProductDTO createProduct(ProductDTO productDTO) {
        //TODO make validators
        Product product = modelMapper.map(productDTO, Product.class);
        productRepository.saveAndFlush(product);

        // Create the product features
        productFeaturesService.createProductFeatures(product, productDTO);

        return modelMapper.map(product, ProductDTO.class);
    }

    public void deleteProduct(long id) {

        productRepository.deleteById(id);
    }


}
