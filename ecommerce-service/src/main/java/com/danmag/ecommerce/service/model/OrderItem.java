package com.danmag.ecommerce.service.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "orders_item")
public class OrderItem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long quantity;


    @ManyToOne(cascade = CascadeType.PERSIST)
    @JsonBackReference
    @JoinColumn(name = "orders_id", referencedColumnName = "id")
    private Order order;
    @OneToOne(cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;


}



