package com.danmag.ecommerce.service.service;

import com.danmag.ecommerce.service.model.Cart;
import com.danmag.ecommerce.service.model.CartItem;
import com.danmag.ecommerce.service.model.Product;
import com.danmag.ecommerce.service.repository.CartItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class CartItemServiceTest {
    @InjectMocks
    private CartItemService cartItemService;
    @Mock
    private CartItemRepository cartItemRepository;
    @Mock
    private ProductService productService;
    @Mock
    private Cart cart;

    @Mock
    private Product product;

    @BeforeEach
    void setUp() {
        cartItemService = new CartItemService(cartItemRepository, productService);
    }



    @Test
    void removeProductFromCart() {
    }


}