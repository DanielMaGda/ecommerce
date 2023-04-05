package com.danmag.ecommerce.service.controller;

import com.danmag.ecommerce.service.api.ApiResponse;
import com.danmag.ecommerce.service.dto.OrderDto;
import com.danmag.ecommerce.service.dto.request.PostOrderRequest;
import com.danmag.ecommerce.service.dto.response.OrderResponse;
import com.danmag.ecommerce.service.repository.AccountsRepository;
import com.danmag.ecommerce.service.repository.OrderItemRepository;
import com.danmag.ecommerce.service.service.AccountsService;
import com.danmag.ecommerce.service.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api/v1/order")
@RestController
public class OrderController {
    private final OrderService orderService;


    public OrderController(OrderService orderService) {
        this.orderService = orderService;

    }

    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponse>> addOrder(@Valid
                                                               @RequestBody PostOrderRequest postOrderRequest
    ) {
        try {
            OrderResponse orderResponse = orderService.postOrder(postOrderRequest);
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Order has been added", orderResponse));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null));
        }
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<OrderDto>>> getAllOrders() {
        try {
            List<OrderDto> response = orderService.getAllOrders();
            if (response.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Success", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        }
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<ApiResponse<List<OrderDto>>> getOrderByUserId(@PathVariable("id") long id) {
        try {
            List<OrderDto> orders = orderService.getOrdersByUserId(id);
            ApiResponse<List<OrderDto>> response = new ApiResponse<>(HttpStatus.OK.value(), "Success", orders);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<List<OrderDto>> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
            return ResponseEntity.badRequest().body(response);
        }
    }


}


