package com.danmag.ecommerce.service.service;


import com.danmag.ecommerce.service.dto.OrderItemsDto;
import com.danmag.ecommerce.service.dto.request.PostOrderRequest;
import com.danmag.ecommerce.service.dto.response.OrderResponse;
import com.danmag.ecommerce.service.enums.ShipmentStatus;
import com.danmag.ecommerce.service.exceptions.InvalidArgumentException;
import com.danmag.ecommerce.service.model.*;
import com.danmag.ecommerce.service.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    private final AccountService accountService;
    private final CartService cartService;

    private final OrderItemService orderItemService;

    private final ProductService productService;

    @Autowired
    public OrderService(OrderRepository orderRepository, ModelMapper modelMapper, AccountService accountService, CartService cartService, OrderItemService orderItemService, ProductService productService) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
        this.accountService = accountService;
        this.cartService = cartService;
        this.orderItemService = orderItemService;
        this.productService = productService;
    }


    public OrderResponse createOrder(PostOrderRequest postOrderRequest) {
        Account account = accountService.getCurrentUserAccount();
        Cart cart = account.getCart();
        if (cart == null || cart.getCartItemList().isEmpty()) {
            throw new InvalidArgumentException("Cart is empty");
        }

        validateCartItems(cart);

        Order order = new Order();
        modelMapper.map(postOrderRequest, order);
        order.setAccount(account);
        order.setDate(new Date());
        order.setTotalPrice(cart.getTotalPrice());
        order.setTotalCargoPrice(cart.getTotalCargoPrice());
        order.setShipped(ShipmentStatus.PENDING);

        List<OrderItem> orderItemList = new ArrayList<>();
        cart.getCartItemList().forEach(cartItem -> {
            Product product = cartItem.getProduct();
            int amount = cartItem.getAmount();
            productService.checkProductStock(product.getId(), amount);
            product.setStock(product.getStock() - amount);
            OrderItem orderItem = orderItemService.addOrderItem(product, amount, order);
            orderItemList.add(orderItem);
        });

        order.setOrderItems(orderItemList);
        orderRepository.save(order);
        cartService.emptyCart();

        return modelMapper.map(order, OrderResponse.class);
    }

    public List<OrderResponse> getAllOrders() {
        List<Order> orderList = orderRepository.findAll();
        return getOrderResponses(orderList);
    }

    public List<OrderResponse> getCurrentAccountOrders() {
        Account account = accountService.getCurrentUserAccount();


        List<Order> orders = account.getOrderList();
        return getOrderResponses(orders);
    }

    private List<OrderResponse> getOrderResponses(List<Order> orders) {
        List<OrderResponse> orderResponseList = new ArrayList<>();

        orders.forEach(order -> {
            OrderResponse orderResponse = modelMapper.map(order, OrderResponse.class);
            orderResponse.setOrderItemsDtoList(order.getOrderItems()
                    .stream()
                    .map(orderItem -> modelMapper.map(orderItem, OrderItemsDto.class))
                    .toList());
            orderResponseList.add(orderResponse);
        });

        return orderResponseList;
    }

    private void validateCartItems(Cart cart) {
        for (CartItem cartItem : cart.getCartItemList()) {
            productService.checkProductStock(cartItem.getProduct().getId(), cartItem.getAmount());
        }
    }
}