package com.danmag.ecommerce.service.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "orders_item")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Min(1)
    private Integer amount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "orders_id")
    private Order order;
    @JsonIgnore
    @OneToOne()
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", amount=" + amount +
                ", product=" + product +
                '}';
    }


}



