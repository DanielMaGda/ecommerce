package com.danmag.ecommerce.service.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@CrossOrigin
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;
    private long price;
    private String description;
    private Integer stock;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product", orphanRemoval = true)
    private List<ProductFeatures> features;

}
