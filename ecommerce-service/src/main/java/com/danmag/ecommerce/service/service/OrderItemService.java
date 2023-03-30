package com.danmag.ecommerce.service.service;

import com.danmag.ecommerce.service.model.OrderItem;
import com.danmag.ecommerce.service.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;

    @Autowired
    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    public void addOrderedProducts(OrderItem orderItem){
        orderItemRepository.save(orderItem);
    }
}

