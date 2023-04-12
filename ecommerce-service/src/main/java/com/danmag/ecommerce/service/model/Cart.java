package com.danmag.ecommerce.service.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @EqualsAndHashCode.Exclude
    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private Account account;


    @DecimalMin("0.0")
    private double totalPrice;

    @DecimalMin("0.0")
    private double totalCartPrice;

    @DecimalMin("0.0")
    private double totalCargoPrice;

    @Valid
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<CartItem> cartItemList;


    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", account=" + account +
                ", totalPrice=" + totalPrice +
                ", totalCartPrice=" + totalCartPrice +
                ", totalCargoPrice=" + totalCargoPrice +
                ", cartItemList=" + cartItemList +
                '}';
    }
}
