package com.danmag.ecommerce.service.controller;

import com.danmag.ecommerce.service.api.ApiResponse;
import com.danmag.ecommerce.service.dto.ProductDTO;
import com.danmag.ecommerce.service.dto.request.UpdateProductRequest;
import com.danmag.ecommerce.service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getAllProductsWithAttributes() {
        try {
            List<ProductDTO> response = productService.getAllProductsWithFeatures();
            return  ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Success", response));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        }

    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ApiResponse<ProductDTO>> getProductById(@PathVariable Long id) {
        try {
            ProductDTO response = productService.getProductById(id);
            return  ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Success", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));          }
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<ApiResponse<ProductDTO>> updateProduct(@Valid @PathVariable long id, @Valid @RequestBody UpdateProductRequest request) throws Exception {
        try {
            ProductDTO response = productService.updateProduct(id, request);
            return  ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Product has been updated", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProductDTO>> addProduct(@Valid @RequestBody ProductDTO productRequestDTO) {
        try {
            ProductDTO response = productService.createProduct(productRequestDTO);
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Product has been added", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable("id") long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Product was deleted", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null));
        }
    }
}



