package com.danmag.ecommerce.service.service;

import com.danmag.ecommerce.service.dto.ProductDTO;
import com.danmag.ecommerce.service.dto.ProductFeatureDTO;
import com.danmag.ecommerce.service.dto.request.UpdateProductRequest;
import com.danmag.ecommerce.service.exceptions.ConflictException;
import com.danmag.ecommerce.service.exceptions.ProductNotFoundException;
import com.danmag.ecommerce.service.exceptions.ProductOutOfStockException;
import com.danmag.ecommerce.service.model.Product;
import com.danmag.ecommerce.service.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    private final ProductFeaturesService productFeaturesService;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductFeaturesService productFeaturesService, ModelMapper modelMapper) {
        this.productRepository = productRepository;

        this.productFeaturesService = productFeaturesService;
        this.modelMapper = modelMapper;
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
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found with id " + id));
        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
        List<ProductFeatureDTO> productFeatureDTO = productFeaturesService.getProductFeatureListForProduct(id);
        productDTO.setFeatures(productFeatureDTO);
        return productDTO;
    }

    public ProductDTO updateProduct(long id, UpdateProductRequest request) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found with id " + id));
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);


        productRepository.saveAndFlush(product);


        productFeaturesService.updateProductFeatures(id, request);

        return productDTO;
    }


    public ProductDTO createProduct(ProductDTO productDTO) {
        if (productRepository.existsByName(productDTO.getName())) {
            throw new ConflictException("Product with this name exist");
        }
        Product product = modelMapper.map(productDTO, Product.class);
        productRepository.saveAndFlush(product);

        // Create the product features
        productFeaturesService.createProductFeatures(product, productDTO);

        return modelMapper.map(product, ProductDTO.class);
    }
    public void deleteProduct(long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isPresent()){
            productRepository.deleteById(id);

        }
        else {
            throw new ProductNotFoundException("Product with "+ id +" dont exist ");
        }
    }

    protected boolean checkProductStock(long productId, int amount) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            if (optionalProduct.get().getStock() < amount) {
                throw new ProductOutOfStockException("Product is out of stock");
            }
            return true;
        }
        throw new ProductNotFoundException("Product with this id was not found");

    }

    public Product fetchProduct(long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            return optionalProduct.get();
        } else {
            throw new ProductNotFoundException("Product with this id was not found");

        }
    }


}
