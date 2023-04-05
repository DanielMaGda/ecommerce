package com.danmag.ecommerce.service.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

//TODO Add Observer for Order to Interact with Shop Magazine when number of product in magazine change
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    @JsonManagedReference
    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

    @NotBlank
    @Size(min = 3, max = 52)
    @Pattern(regexp = "^[a-zA-Z\\s]+$")
    private String shipName;

    @NotBlank
    @Size(min = 3, max = 240)
    @Pattern(regexp = "[0-9a-zA-Z #,-]+")
    private String shipAddress;

    @NotBlank
    @Size(min = 3, max = 240)
    @Pattern(regexp = "[0-9a-zA-Z #,-]+")
    private String billingAddress;

    @NotBlank
    @Size(min = 3, max = 100)
    @Pattern(regexp = "^[a-zA-Z\\s]+$")
    private String city;

    @NotBlank
    @Size(min = 3, max = 40)
    @Pattern(regexp = "^[a-zA-Z\\s]+$")
    private String state;

    @NotBlank
    @Size(min = 5, max = 6)
    @Pattern(regexp = "^[0-9]*$")
    private String zip;

    @NotBlank
    @Size(min = 3, max = 40)
    @Pattern(regexp = "^[a-zA-Z\\s]+$")
    private String country;

    @NotBlank
    @Size(min = 9, max = 12)
    @Pattern(regexp = "[0-9]+")
    private String phone;

    @NotNull
    private Float totalPrice;

    @NotNull
    private Float totalCargoPrice;


    @Type(type = "timestamp")
    private Date date;

    @NotNull
    private Integer shipped;

    @NotNull
    @Size(min = 2, max = 50)
    private String cargoFirm;

    @NotNull
    @Size(min = 2, max = 50)
    private String trackingNumber;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderItems=" + orderItems +
                ", shipName='" + shipName + '\'' +
                ", shipAddress='" + shipAddress + '\'' +
                ", billingAddress='" + billingAddress + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zip='" + zip + '\'' +
                ", country='" + country + '\'' +
                ", phone='" + phone + '\'' +
                ", totalPrice=" + totalPrice +
                ", totalCargoPrice=" + totalCargoPrice +
                ", date=" + date +
                ", shipped=" + shipped +
                ", cargoFirm='" + cargoFirm + '\'' +
                ", trackingNumber='" + trackingNumber + '\'' +
                '}';
    }

}
