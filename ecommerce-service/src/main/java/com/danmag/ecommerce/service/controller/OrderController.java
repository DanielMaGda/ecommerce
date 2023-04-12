package com.danmag.ecommerce.service.controller;

import com.danmag.ecommerce.service.api.ApiResponse;
import com.danmag.ecommerce.service.dto.request.PostOrderRequest;
import com.danmag.ecommerce.service.dto.response.OrderResponse;
import com.danmag.ecommerce.service.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api/v1/orders")
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
            OrderResponse orderResponse = orderService.createOrder(postOrderRequest);
            return ResponseEntity
                    .ok(new ApiResponse<>(HttpStatus.OK.value(), "Order has been added", orderResponse));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null));
        }
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getAllOrders() {
        try {
            List<OrderResponse> response = orderService.getAllOrders();
            if (response.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Success", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getOrderByUserId(@PathVariable("id") long id) {
        try {
            List<OrderResponse> orders = orderService.getCurrentAccountOrders();
            ApiResponse<List<OrderResponse>> response = new ApiResponse<>(HttpStatus.OK.value(), "Success", orders);
            return ResponseEntity.ok(response);
        } catch (Exception e) {

            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        }
    }


}


