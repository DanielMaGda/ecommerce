package com.danmag.ecommerce.service.model;

import lombok.Data;

import javax.persistence.*;
@Data
@Entity
@Table(name = "brand")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;


}
