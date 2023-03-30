package com.danmag.ecommerce.service.controller;

import com.danmag.ecommerce.service.api.ApiResponse;
import com.danmag.ecommerce.service.dto.ProductDTO;
import com.danmag.ecommerce.service.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin

@RestController
@RequestMapping(value = "/api/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping
    public List<ProductDTO> getAllProductsWithAttributes() {
        return productService.getAllProductsWithFeatures();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) throws Exception {
        ProductDTO productDTO = productService.getProductById(id);
        return ResponseEntity.ok(productDTO);
    }
    @PatchMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable long id, @RequestBody ProductDTO productRequestDTO) throws Exception {
        ProductDTO productDto = productService.updateProduct(id, productRequestDTO);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK.value(), "Success"), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addProduct(@RequestBody ProductDTO productRequestDTO) {

        productService.createProduct(productRequestDTO);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK.value(), "Success"), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable("id") long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK.value(), "Success"), HttpStatus.OK);
    }


}
