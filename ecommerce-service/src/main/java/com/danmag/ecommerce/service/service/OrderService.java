package com.danmag.ecommerce.service.service;


import com.danmag.ecommerce.service.dto.OrderItemsDto;
import com.danmag.ecommerce.service.model.Account;
import com.danmag.ecommerce.service.repository.OrderItemRepository;
import com.danmag.ecommerce.service.repository.OrderRepository;
import com.danmag.ecommerce.service.model.Order;
import com.danmag.ecommerce.service.repository.AccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
//TODO You could use the Strategy pattern to implement different shipping options for the online shop.
// Each shipping option would be represented
// by a separate class that implements a common interface and could be easily swapped in and out at runtime.
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final AccountsRepository accountsRepository;


    @Autowired
    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository, AccountsRepository accountsRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.accountsRepository = accountsRepository;
    }

    public Order addOrder(OrderItemsDto orderItemsDto, Authentication authentication) throws Exception {
        if (authentication == null) {
            throw new Exception("User is not logged in");

        } else {
            Order order = new Order();
            order.setStatus("pending");
            orderItemsDto.getOrderItems().forEach(oi -> oi.setOrder(order));
            order.setOrderItems(orderItemsDto.getOrderItems());
            String username = authentication.getName();
            Optional<Account> customers = accountsRepository.findByUserName(username);
            customers.ifPresent(order::setAccount);
            return orderRepository.saveAndFlush(order);
        }


    }

    public List<Order> orderList() {
        return orderRepository.findAll();
    }


    public Order getOrdersByUserId(long id) {
        Optional<Order> order = orderRepository.findByAccountId(id);
        if (order.isPresent()) {
            return order.get();
        }
        throw new NoSuchElementException("User with id" + id + " dont have any orders");

    }
}
