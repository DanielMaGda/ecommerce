package com.danmag.ecommerce.service.service;

import com.danmag.ecommerce.service.dto.FeatureDTO;
import com.danmag.ecommerce.service.dto.FeatureValueDTO;
import com.danmag.ecommerce.service.dto.ProductDTO;
import com.danmag.ecommerce.service.dto.ProductFeatureDTO;
import com.danmag.ecommerce.service.dto.request.UpdateProductRequest;
import com.danmag.ecommerce.service.exceptions.ConflictException;
import com.danmag.ecommerce.service.exceptions.ProductNotFoundException;
import com.danmag.ecommerce.service.exceptions.ProductOutOfStockException;
import com.danmag.ecommerce.service.model.Product;
import com.danmag.ecommerce.service.model.ProductFeatures;
import com.danmag.ecommerce.service.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductService productService;
    @Mock
    private ModelMapper modelMapper;
    @Mock

    private ProductFeaturesService productFeaturesService;

    @BeforeEach
    void setUp() {
        productService = new ProductService(productRepository, productFeaturesService, modelMapper);
    }

    @ParameterizedTest
    @MethodSource("provideProductLists")
    void getAllProductsWithFeatures(List<Product> productList, List<ProductDTO> productDTOList) {
        when(productRepository.findAll()).thenReturn(productList);
        when(productFeaturesService.getProductFeatureListForProduct(anyLong())).thenReturn(Arrays.asList(
                ProductFeatureDTO.builder()
                        .id(1)
                        .feature(FeatureDTO.builder()
                                .fullName("Feature 1").build())
                        .featureValue(FeatureValueDTO.builder()
                                .fullName("Feature Value 2").build())
                        .build(),
                ProductFeatureDTO.builder()
                        .id(2)
                        .feature(FeatureDTO.builder()
                                .fullName("Feature 1").build())
                        .featureValue(FeatureValueDTO.builder()
                                .fullName("Feature Value 2").build())
                        .build()
        ));
        when(modelMapper.map(productList.get(0), ProductDTO.class)).thenReturn(productDTOList.get(0));
        when(modelMapper.map(productList.get(1), ProductDTO.class)).thenReturn(productDTOList.get(1));

        List<ProductDTO> result = productService.getAllProductsWithFeatures();

        assertAll("Product 1",
                () -> assertEquals("Product 1", result.get(0).getName()),
                () -> assertEquals("Feature 1", result.get(0).getFeatures().get(0).getFeature().getFullName()),
                () -> assertEquals("Feature Value 2", result.get(0).getFeatures().get(0).getFeatureValue().getFullName()),
                () -> assertEquals("Feature 1", result.get(0).getFeatures().get(1).getFeature().getFullName()),
                () -> assertEquals("Feature Value 2", result.get(0).getFeatures().get(1).getFeatureValue().getFullName())
        );
        assertAll("Product 2",
                () -> assertEquals("Product 2", result.get(1).getName()),
                () -> assertEquals("Feature 1", result.get(1).getFeatures().get(0).getFeature().getFullName()),
                () -> assertEquals("Feature Value 2", result.get(1).getFeatures().get(0).getFeatureValue().getFullName()),
                () -> assertEquals("Feature 1", result.get(1).getFeatures().get(1).getFeature().getFullName()),
                () -> assertEquals("Feature Value 2", result.get(1).getFeatures().get(1).getFeatureValue().getFullName())
        );


    }


    @Test
    void getProductByIdTest() {
        long productId = 1L;

        Product product = Product.builder()
                .id(productId)
                .name("Product 1")
                .build();
        ProductDTO productDTO = ProductDTO.builder()
                .id(productId)
                .name("Product 1")
                .build();
        List<ProductFeatureDTO> featureDTOList = Arrays.asList(
                ProductFeatureDTO.builder()
                        .id(1)
                        .feature(FeatureDTO.builder()
                                .fullName("Feature 1").build())
                        .featureValue(FeatureValueDTO.builder()
                                .fullName("Feature Value 1").build())
                        .build(),
                ProductFeatureDTO.builder()
                        .id(2)
                        .feature(FeatureDTO.builder()
                                .fullName("Feature 2").build())
                        .featureValue(FeatureValueDTO.builder()
                                .fullName("Feature Value 2").build())
                        .build()
        );

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(modelMapper.map(product, ProductDTO.class)).thenReturn(productDTO);
        when(productFeaturesService.getProductFeatureListForProduct(productId)).thenReturn(featureDTOList);

        ProductDTO result = productService.getProductById(productId);

        assertEquals(productDTO.getId(), result.getId());
        assertEquals(productDTO.getName(), result.getName());
        assertEquals(featureDTOList.size(), result.getFeatures().size());
        assertEquals(featureDTOList.get(0).getId(), result.getFeatures().get(0).getId());
        assertEquals(featureDTOList.get(0).getFeature().getFullName(), result.getFeatures().get(0).getFeature().getFullName());
        assertEquals(featureDTOList.get(0).getFeatureValue().getFullName(), result.getFeatures().get(0).getFeatureValue().getFullName());
        assertEquals(featureDTOList.get(1).getId(), result.getFeatures().get(1).getId());
        assertEquals(featureDTOList.get(1).getFeature().getFullName(), result.getFeatures().get(1).getFeature().getFullName());
        assertEquals(featureDTOList.get(1).getFeatureValue().getFullName(), result.getFeatures().get(1).getFeatureValue().getFullName());
    }

    @Test
    void testGetProductByIdThrowsException() {
        long invalidId = 100L; // assume that there's no product with this ID
        when(productRepository.findById(invalidId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> {
            productService.getProductById(invalidId);
        });
    }

    @Test
    void testUpdateProductSuccess() {
        // create a mock product
        Product existingProduct = Product.builder()
                .id(1L)
                .name("Product 1")
                .price((10.0))
                .stock(20)
                .build();

        // create a mock update request
        UpdateProductRequest updateRequest = UpdateProductRequest.builder()
                .name("New Product 1")
                .price((12.0))
                .stock(25)
                .build();

        // create a mock updated product
        Product product = Product.builder()
                .id(1L)
                .name("New Product 1")
                .price((12.0))
                .stock(25)
                .build();
        ProductDTO productDTO = ProductDTO.builder()
                .id(1L)
                .name("New Product 1")
                .price((12.0))
                .stock(25)
                .build();
        // mock the repository
        when(productRepository.findById(existingProduct.getId())).thenReturn(Optional.of(existingProduct));
        when(productRepository.saveAndFlush(product)).thenReturn(product);
        when(modelMapper.map(any(Product.class), eq(ProductDTO.class))).thenReturn(productDTO);


        // mock the service
        doNothing().when(productFeaturesService).updateProductFeatures(product.getId(), updateRequest);

        // call the method to be tested
        ProductDTO result = productService.updateProduct(existingProduct.getId(), updateRequest);

        // assert the result
        assertEquals(product.getId(), result.getId());
        assertEquals(product.getName(), result.getName());
        assertEquals(product.getPrice(), result.getPrice(), 0.001);
        assertEquals(product.getStock(), result.getStock());
    }


    @Test
    void testUpdateProductWithNonexistentId() {
        // Arrange
        long nonExistentId = 9999L;
        UpdateProductRequest request = new UpdateProductRequest();
        request.setName("New Product Name");
        request.setPrice(9.99);
        request.setStock(10);

        when(productRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ProductNotFoundException.class, () -> productService.updateProduct(nonExistentId, request));
    }

    @Test
    void testCreateProduct() {
        // Arrange
        ProductDTO productDTO = ProductDTO.builder().build();
        Product product = Product.builder().build();
        List<ProductFeatures> productFeaturesList = List.of();

        when(productRepository.existsByName(productDTO.getName())).thenReturn(false);
        when(modelMapper.map(productDTO, Product.class)).thenReturn(product);
        when(productFeaturesService.createProductFeatures(product, productDTO)).thenReturn(productFeaturesList);
        // Act
        productService.createProduct(productDTO);

        // Assert
        verify(productRepository).existsByName(productDTO.getName());
        verify(productRepository).saveAndFlush(product);
        verify(productFeaturesService).createProductFeatures(product, productDTO);
        verify(modelMapper).map(product, ProductDTO.class);
    }

    @Test
    void testCreateProductWithExistingName() {
        // Arrange
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Test Product");
        when(productRepository.existsByName(productDTO.getName())).thenReturn(true);

        // Act & Assert
        assertThrows(ConflictException.class, () -> productService.createProduct(productDTO));
        verify(productRepository).existsByName(productDTO.getName());
        verify(productRepository, never()).saveAndFlush(any());
        verify(productFeaturesService, never()).createProductFeatures(any(), any());
        verify(modelMapper, never()).map(any(), eq(ProductDTO.class));
    }

    @Test
    void testDeleteProduct() {
        // Arrange
        long id = 1L;
        Product product = new Product();
        product.setId(id);

        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        // Act
        productService.deleteProduct(id);

        // Assert
        verify(productRepository).deleteById(id);
    }

    @Test
    void testDeleteProductNotFound() {
        // Arrange
        long id = 1L;

        when(productRepository.findById(id)).thenReturn(Optional.empty());

        // Act and Assert
        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, () -> {
            productService.deleteProduct(id);
        });

        assertEquals("Product with " + id + " dont exist ", exception.getMessage());
    }
    @Test
    void testCheckProductStock(){
        long productId = 1L;
        int amount = 10;

        Product product = new Product();
        product.setId(productId);
        product.setStock(5);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        assertThrows(ProductOutOfStockException.class, () -> productService.checkProductStock(productId, amount));

        // Verify that no exception is thrown when the amount is less than or equal to the stock
        productService.checkProductStock(productId, 3);
    }
    @Test
    void testFetchProduct() {
        long productId = 1L;

        // Create a product with the given ID
        Product product = new Product();
        product.setId(productId);

        // Mock the product repository to return the product
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // Verify that the fetchProduct method returns the product with the given ID
        assertEquals(product, productService.fetchProduct(productId));

        // Verify that a ProductNotFoundException is thrown when the product with the given ID does not exist
        long nonExistentProductId = 2L;
        when(productRepository.findById(nonExistentProductId)).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> productService.fetchProduct(nonExistentProductId));
    }



    private static Stream<Arguments> provideProductLists() {
        List<Product> productList = Arrays.asList(
                Product.builder()
                        .id(1L)
                        .name("Product 1")
                        .build(),
                Product.builder()
                        .id(2L)
                        .name("Product 2")
                        .build()
        );
        List<ProductDTO> productDTOList = Arrays.asList(
                ProductDTO.builder()
                        .id(1L)
                        .name("Product 1")
                        .build(),
                ProductDTO.builder()
                        .id(2L)
                        .name("Product 2")
                        .build()
        );
        return Stream.of(Arguments.of(productList, productDTOList));
    }
}