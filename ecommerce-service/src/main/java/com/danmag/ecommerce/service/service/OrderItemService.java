package com.danmag.ecommerce.service.service;

import com.danmag.ecommerce.service.model.Order;
import com.danmag.ecommerce.service.model.OrderItem;
import com.danmag.ecommerce.service.model.Product;
import com.danmag.ecommerce.service.repository.OrderItemRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    public OrderItem addOrderItem(Product product, int amount, Order order) {
        if (product == null || order == null || amount <= 0) {
            throw new IllegalArgumentException("Invalid input parameters for creating order item.");
        }

        OrderItem orderItem = OrderItem.builder()
                .product(product)
                .amount(amount)
                .order(order)
                .build();
        orderItem = orderItemRepository.saveAndFlush(orderItem);

        return orderItem;
    }
}