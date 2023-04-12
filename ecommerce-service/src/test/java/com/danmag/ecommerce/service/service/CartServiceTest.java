package com.danmag.ecommerce.service.service;

import com.danmag.ecommerce.service.dto.CartDto;
import com.danmag.ecommerce.service.dto.CartItemDto;
import com.danmag.ecommerce.service.dto.ProductDetailsDto;
import com.danmag.ecommerce.service.dto.request.AddToCartRequest;
import com.danmag.ecommerce.service.dto.response.CartResponse;
import com.danmag.ecommerce.service.exceptions.CartNotFoundException;
import com.danmag.ecommerce.service.model.Account;
import com.danmag.ecommerce.service.model.Cart;
import com.danmag.ecommerce.service.model.CartItem;
import com.danmag.ecommerce.service.model.Product;
import com.danmag.ecommerce.service.repository.CartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class CartServiceTest {
    @Mock
    private CartRepository cartRepository;
    @Mock
    private AccountService accountService;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private CartService cartService;
    @Mock
    private CartItemService cartItemService;
    @Mock
    private ProductService productService;


    @BeforeEach
    void setUp() {
        cartService = new CartService(cartRepository, accountService, productService, modelMapper, cartItemService);
    }

    @ParameterizedTest
    @MethodSource("provideCartLists")
    void getAllCarts(List<Cart> cartList, List<CartDto> cartDtoLists) {
        // given
//        List<Cart> cartList = Arrays.asList(
//                Cart.builder()
//                        .id(1)
//                        .totalCartPrice(1400)
//                        .build(),
//                Cart.builder()
//                        .id(2)
//                        .totalCartPrice(2000)
//                        .build()
//        );
//        List<CartDto> cartDtoList = Arrays.asList(
//                CartDto.builder()
//                        .totalPrice(1f)
//                        .totalCartPrice(1400f)
//                        .build(),
//                CartDto.builder()
//                        .totalPrice(2f)
//                        .totalCartPrice(2000f)
//                        .build()
//        );
        when(cartRepository.findAll()).thenReturn(cartList);
        when(modelMapper.map(cartList.get(0), CartDto.class)).thenReturn(cartDtoLists.get(0));
        when(modelMapper.map(cartList.get(1), CartDto.class)).thenReturn(cartDtoLists.get(1));

        // when
        List<CartDto> returnedCartDtoList = cartService.getAllCarts();

        // then
        assertEquals(2, returnedCartDtoList.size());
        assertEquals(1100, returnedCartDtoList.get(0).getTotalPrice());
        assertEquals(1250, returnedCartDtoList.get(1).getTotalPrice());
        verify(cartRepository, times(1)).findAll();
        verify(modelMapper, times(1)).map(cartList.get(0), CartDto.class);
        verify(modelMapper, times(1)).map(cartList.get(1), CartDto.class);
    }

    @Test
    void should_throw_exception_when_carts_not_present() {
        //given
        List<Cart> emptyCartList = new ArrayList<>();
        when(cartRepository.findAll()).thenReturn(emptyCartList);

        //when,then
        assertThrows(CartNotFoundException.class, () -> cartService.getAllCarts());
        verify(cartRepository, times(1)).findAll();
        verify(modelMapper, never()).map(any(), eq(CartDto.class));

    }


    @Test
    void should_get_cart_by_user_id() {
        long id = 1L;
        Cart cart = Cart.builder()
                .totalCartPrice(100)
                .totalCartPrice(15)
                .totalPrice(115)
                .build();
        CartDto cartResponse = CartDto.builder()
                .totalCartPrice(100.0)
                .totalCartPrice(15.0)
                .totalPrice(115.0)
                .build();
        when(cartRepository.findByAccountId(id)).thenReturn(Optional.of(cart));
        when(modelMapper.map(cart, CartDto.class)).thenReturn(cartResponse);

        CartDto expectedCartResponse = cartService.getCartByAccountId(id);

        assertEquals(cartResponse, expectedCartResponse);
        verify(cartRepository, times(1)).findByAccountId(id);
        verify(modelMapper, times(1)).map(cart, CartDto.class);
    }

    @Test
    void should_thrown_exception_when_cart_not_present() {
        long id = 1L;
        when(cartRepository.findByAccountId(id)).thenReturn(Optional.empty());

        assertThrows(CartNotFoundException.class, () -> cartService.getCartByAccountId(id));
        verify(cartRepository, times(1)).findByAccountId(id);
        verify(modelMapper, never()).map(any(), eq(CartDto.class));
    }

    @Test
    void should_add_valid_product_to_current_account_cart() {
        //given
        Account account = Account.builder()
                .userName("John Doe")
                .id(1L)
                .build();
        Product product = Product.builder()
                .id(1L)
                .name("Product 1")
                .price(10.0)
                .build();
        AddToCartRequest addToCartRequest = new AddToCartRequest();
        addToCartRequest.setAmount(2);
        addToCartRequest.setProductId(1L);
        Cart cart = new Cart();
        CartDto cartDto = new CartDto();
        ProductDetailsDto productDetailsDto = new ProductDetailsDto();
        productDetailsDto.setId(1L);
        productDetailsDto.setName("Product 1");
        productDetailsDto.setPrice(10.0);
        // create a new CartItem object with desired properties
        CartItem cartItem = CartItem.builder()
                .cart(cart)
                .product(product)
                .amount(addToCartRequest.getAmount())
                .build();
        CartItemDto cartItemDto = CartItemDto.builder()
                .cart(cartDto)
                .product(productDetailsDto)
                .amount(addToCartRequest.getAmount())
                .build();

        List<CartItem> cartItemList = Collections.singletonList(CartItem.builder()
                .cart(cart)
                .product(product)
                .amount(addToCartRequest.getAmount())
                .build());


        // add the new CartItem object to the Cart object
        cart.setCartItemList(cartItemList);

        // set the account for the cart
        cart.setAccount(account);
        when(accountService.getCurrentUserAccount()).thenReturn(account);
        when(productService.fetchProduct(1L)).thenReturn(product);
        when(modelMapper.map(cartItem, CartItemDto.class)).thenReturn(cartItemDto);
        when(cartRepository.findByAccount(account)).thenReturn(Optional.of(cart));
        when(cartItemService.addOrUpdateCartItem(cart, product.getId(), addToCartRequest.getAmount())).thenReturn(cartItemList.get(0));

        //when
        CartResponse cartResponse = cartService.addProductToCart(addToCartRequest);
        //then
        verify(accountService, times(1)).getCurrentUserAccount();
        verify(productService, times(1)).fetchProduct(1L);
        verify(cartRepository, times(1)).findByAccount(account);
        verify(cartItemService, times(1)).addOrUpdateCartItem(any(Cart.class), anyLong(), anyInt());


        assertEquals(20.0, cartResponse.getTotalCartPrice());
        assertEquals(15.0, cartResponse.getTotalCargoPrice());
        assertEquals(35.0, cartResponse.getTotalPrice());
    }

    @Test
    void removeProductFromCart_ShouldRemoveCartItemFromCart() {
        // Setup
        long productId = 1L;
        Account account = new Account();
        account.setId(1L);
        Cart cart = new Cart();
        cart.setAccount(account);
        cart.setTotalCartPrice(100);
        cart.setTotalPrice(1000);
        Product product = Product.builder()
                .id(1L)
                .name("Product 1")
                .price(10.0)
                .build();
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setAmount(1);
        cart.setCartItemList(List.of(cartItem));
        when(accountService.getCurrentUserAccount()).thenReturn(account);
        when(cartRepository.findByAccount(account)).thenReturn(Optional.of(cart));
        when(productService.fetchProduct(product.getId())).thenReturn(product);

        // Exercise
        cartService.removeProductFromCart(productId);

        // Verify
        ArgumentCaptor<Cart> cartCaptor = ArgumentCaptor.forClass(Cart.class);
        verify(cartRepository).save(cartCaptor.capture());
        assertAll("cart item was removed",
                () -> assertEquals(0, cart.getCartItemList().size()),
                () -> assertEquals(0, cart.getTotalCartPrice()),
                () -> assertEquals(0, cart.getTotalCargoPrice()),
                () -> assertEquals(0, cart.getTotalPrice())
        );
    }

    @Test
    void removeProductFromCart_ShouldRemoveCartItemWithProductId1FromCart() {
        // Setup
        long productId1 = 1L;
        long productId2 = 2L;
        Account account = new Account();
        account.setId(1L);
        Cart cart = new Cart();
        cart.setAccount(account);
        cart.setTotalCartPrice(200);
        cart.setTotalPrice(2000);
        Product product1 = Product.builder()
                .id(productId1)
                .name("Product 1")
                .price(10.0)
                .build();
        Product product2 = Product.builder()
                .id(productId2)
                .name("Product 2")
                .price(20.0)
                .build();
        CartItem cartItem1 = new CartItem();
        cartItem1.setProduct(product1);
        cartItem1.setAmount(1);
        CartItem cartItem2 = new CartItem();
        cartItem2.setProduct(product2);
        cartItem2.setAmount(2);
        cart.setCartItemList(List.of(cartItem1, cartItem2));
        when(accountService.getCurrentUserAccount()).thenReturn(account);
        when(cartRepository.findByAccount(account)).thenReturn(Optional.of(cart));
        when(productService.fetchProduct(productId1)).thenReturn(product1);
        when(cartItemService.removeProductFromCart(cart, productId1))
                .thenReturn(List.of(cartItem2));
        // Exercise
        cartService.removeProductFromCart(productId1);

        // Verify
        ArgumentCaptor<Cart> cartCaptor = ArgumentCaptor.forClass(Cart.class);
        verify(cartRepository).save(cartCaptor.capture());
        Cart savedCart = cartCaptor.getValue();
        assertAll("cart item with product ID 1 was removed",
                () -> assertEquals(1, savedCart.getCartItemList().size()),
                () -> assertEquals(productId2, savedCart.getCartItemList().get(0).getProduct().getId()),
                () -> assertEquals(40.0, savedCart.getTotalCartPrice()),
                () -> assertEquals(55.0, savedCart.getTotalPrice())
        );
    }

    private static Stream<Arguments> provideCartLists() {
        List<Cart> cartList = Arrays.asList(
                Cart.builder()
                        .id(1L)
                        .totalCartPrice(1000)
                        .totalCargoPrice(100)
                        .totalPrice(1100)
                        .build(),
                Cart.builder()
                        .id(2L)
                        .totalCartPrice(1100)
                        .totalCargoPrice(150)
                        .totalPrice(1250)
                        .build()
        );
        List<CartDto> cartDtoList = Arrays.asList(
                CartDto.builder()
                        .totalCartPrice(100.0)
                        .totalCargoPrice(1000.0)
                        .totalPrice(1100.0)
                        .build(),
                CartDto.builder()
                        .totalCartPrice(150.0)
                        .totalCargoPrice(1100.0)
                        .totalPrice(1250.0)
                        .build()
        );
        return Stream.of(
                Arguments.of(cartList, cartDtoList));
    }
}