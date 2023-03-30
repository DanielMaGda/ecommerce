package com.danmag.ecommerce.service.controller;

import com.danmag.ecommerce.service.api.ApiResponse;
import com.danmag.ecommerce.service.dto.OrderItemsDto;
import com.danmag.ecommerce.service.model.Order;
import com.danmag.ecommerce.service.repository.AccountsRepository;
import com.danmag.ecommerce.service.repository.OrderItemRepository;
import com.danmag.ecommerce.service.service.AccountsService;
import com.danmag.ecommerce.service.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/order")
@RestController
public class OrderController {
    private final OrderService orderService;
    private final AccountsService accountsService;
    private final AccountsRepository accountsRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderController(OrderService orderService, AccountsService accountsService, AccountsRepository accountsRepository, OrderItemRepository orderItemRepository) {
        this.orderService = orderService;
        this.accountsService = accountsService;
        this.accountsRepository = accountsRepository;
        this.orderItemRepository = orderItemRepository;
    }
    //TODO Something not working
    @PostMapping
    public ResponseEntity<ApiResponse> addOrder(
            @RequestBody OrderItemsDto orderItemsList, Authentication authentication
    ) {
        try {
            orderService.addOrder(orderItemsList, authentication);
            return new ResponseEntity<>(new ApiResponse(HttpStatus.OK.value(), "Success"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.OK.value(), "Success"), HttpStatus.OK);

        }


    }

    @GetMapping()
    public List<Order> getAllOrders() {

        return orderService.orderList();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getOrderByUserId(@PathVariable("id") long id) {
        try {
            var order = orderService.getOrdersByUserId(id);
            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.OK.value(), "Success"), HttpStatus.OK);

        }

    }

}


