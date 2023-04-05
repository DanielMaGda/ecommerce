package com.danmag.ecommerce.service.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderDto {

    private Long id;

    private String shipName;

    private String shipAddress;

    private String billingAddress;

    private String city;

    private String state;

    private String zip;

    private String country;

    private String phone;

    private Float totalPrice;

    private Float totalCargoPrice;

    private Long date;

    private Integer shipped;

    private String cargoFirm;

    private String trackingNumber;

    private List<OrderItemsDto> orderItems;


}
