package com.danmag.ecommerce.service.service;


import com.danmag.ecommerce.service.dto.OrderDto;
import com.danmag.ecommerce.service.dto.request.PostOrderRequest;
import com.danmag.ecommerce.service.dto.response.OrderResponse;
import com.danmag.ecommerce.service.exceptions.AccountNotFoundException;
import com.danmag.ecommerce.service.exceptions.InvalidArgumentException;
import com.danmag.ecommerce.service.model.*;
import com.danmag.ecommerce.service.repository.OrderItemRepository;
import com.danmag.ecommerce.service.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.*;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ModelMapper modelMapper;

    private final AccountsService accountsService;
    private final CartService cartService;

    private final OrderItemService orderItemService;

    @Autowired
    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository, ModelMapper modelMapper, AccountsService accountsService, CartService cartService, OrderItemService orderItemService) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.modelMapper = modelMapper;
        this.accountsService = accountsService;
        this.cartService = cartService;
        this.orderItemService = orderItemService;
    }


    public OrderResponse postOrder(PostOrderRequest postOrderRequest) throws AccessDeniedException {

        Optional<Account> account = accountsService.getAccount();
        if (account.isEmpty()) {
            throw new AccountNotFoundException("Account is null");
        }

        Cart cart = account.get().getCart();
        if (cart == null || cart.getCartItemList() == null) {
            throw new InvalidArgumentException("Cart is not valid");
        }

        boolean outOfStock = cart.getCartItemList().stream()
                .anyMatch(cartItem -> cartItem.getProduct().getStock() < cartItem.getAmount());

        if (outOfStock) {
            throw new InvalidArgumentException("A product in your cart is out of stock.");
        }

        Order order = Order.builder()
                .account(account.get())
                .shipName(postOrderRequest.getShipName())
                .phone(postOrderRequest.getPhone())
                .shipAddress(postOrderRequest.getShipAddress())
                .billingAddress(postOrderRequest.getBillingAddress())
                .city(postOrderRequest.getCity())
                .country(postOrderRequest.getCountry())
                .state(postOrderRequest.getState())
                .zip(postOrderRequest.getZip())
                .trackingNumber(postOrderRequest.getTrackingNumber())
                .cargoFirm(postOrderRequest.getCargoFirm())
                .date(new Date())
                .totalPrice(cart.getTotalPrice())
                .totalCargoPrice(cart.getTotalCargoPrice())
                .shipped(1)
                .build();

        orderRepository.save(order);

        cart.getCartItemList().forEach(cartItem -> {
            Product product = cartItem.getProduct();
            int amount = cartItem.getAmount();
            product.setStock(product.getStock() - amount);
            orderItemService.addOrderItem(product, amount, order);
        });

        cartService.emptyCart();

        return new OrderResponse();
    }

    public List<OrderDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(order -> modelMapper.map(order, OrderDto.class)).toList();
    }


    public List<OrderDto> getOrdersByUserId(long id) throws AccessDeniedException {
        Optional<Account> account = accountsService.getAccount();
        if (account.isPresent()) {
            List<Order> orders = account.get().getOrderList();
            return orders.stream().map(order -> modelMapper.map(order, OrderDto.class)).toList();
        } else {
            // handle the case where the user is not authenticated
            throw new AccessDeniedException("User not authenticated");

        }

    }
}
