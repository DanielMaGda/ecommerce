package com.danmag.ecommerce.service;

import com.danmag.ecommerce.service.controller.ProductController;
import com.danmag.ecommerce.service.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@AutoConfigureMockMvc
class EcommerceApplicationTests {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;
    @InjectMocks
    private ProductController productController;
    @Mock
    private ProductService productService;

//    @Test
//    void testGetAllProductsWithAttributes() {
//        List<ProductDTO> productDTOList = Arrays.asList(new ProductDTO(), new ProductDTO());
//        Mockito.when(productService.getAllProductsWithFeatures()).thenReturn(productDTOList);
//        ResponseEntity<List<ProductDTO>> responseEntity = productController.getAllProductsWithAttributes();
//        List<ProductDTO> result = responseEntity.getBody();
//        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        Assertions.assertEquals(productDTOList, result);
//    }
//
//    @Test
//    void testGetProductById() throws Exception {
//        // Prepare mock data
//        ProductDTO product = new ProductDTO();
//        Long id = 1L;
//        Mockito.when(productService.getProductById(id)).thenReturn(product);
//
//        // Execute the method to test
//        ResponseEntity<ProductDTO> result = productController.getProductById(id);
//
//        // Verify the result
//        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
//        Assertions.assertEquals(product, result.getBody());
//    }
//
//    @Test
//    void testDeleteProduct() {
//        // Prepare mock data
//        long id = 1;
//        ApiResponse expectedResponse = new ApiResponse(HttpStatus.OK.value(), "Success");
//        Mockito.doNothing().when(productService).deleteProduct(id);
//
//        // Execute the method to test
//        ResponseEntity<ApiResponse> result = productController.deleteProduct(id);
//
//        // Verify the result
//        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
//        Assertions.assertEquals(expectedResponse.getStatus(), result.getBody().getStatus());
//        Assertions.assertEquals(expectedResponse.getMessage(), result.getBody().getMessage());
//        Mockito.verify(productService, Mockito.times(1)).deleteProduct(id);
//    }
//
//    @Test
//    void testUpdateProduct() throws Exception {
//        // Prepare mock data
//        long id = 1L;
//        ProductDTO productDTO = new ProductDTO();
//        ApiResponse expectedResponse = new ApiResponse(HttpStatus.OK.value(), "Success");
//        Mockito.when(productService.updateProduct(id, productDTO)).thenReturn(productDTO);
//
//        // Execute the method to test
//        ResponseEntity<ApiResponse> result = productController.updateProduct(id, productDTO);
//
//        // Verify the result
//        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
//        Assertions.assertEquals(expectedResponse.getStatus(), result.getBody().getStatus());
//        Assertions.assertEquals(expectedResponse.getMessage(), result.getBody().getMessage());
//        Mockito.verify(productService, Mockito.times(1)).updateProduct(id, productDTO);
//    }
//
//    @Test
//    void testAddProduct() {
//        // Prepare mock data
//        ProductDTO productDTO = new ProductDTO();
//        ApiResponse expectedResponse = new ApiResponse(HttpStatus.OK.value(), "Success");
//        Mockito.doNothing().when(productService).createProduct(productDTO);
//
//        // Execute the method to test
//        ResponseEntity<ApiResponse> result = productController.addProduct(productDTO);
//
//        // Verify the result
//        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
//        Assertions.assertEquals(expectedResponse.getStatus(), result.getBody().getStatus());
//        Assertions.assertEquals(expectedResponse.getMessage(), result.getBody().getMessage());
//        Mockito.verify(productService, Mockito.times(1)).createProduct(productDTO);
//    }

}
