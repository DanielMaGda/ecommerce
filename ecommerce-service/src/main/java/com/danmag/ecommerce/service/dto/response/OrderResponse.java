package com.danmag.ecommerce.service.dto.response;

import com.danmag.ecommerce.service.dto.OrderItemsDto;
import com.danmag.ecommerce.service.enums.ShipmentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

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

    private ShipmentStatus shipped;

    private String cargoFirm;

    private String trackingNumber;

    private List<OrderItemsDto> orderItemsDtoList;

}