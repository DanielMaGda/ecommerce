package com.danmag.ecommerce.service.dto;

import com.danmag.ecommerce.service.model.OrderItem;

import java.util.List;

public class OrderItemsDto {

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    private List<OrderItem> orderItems;

}
