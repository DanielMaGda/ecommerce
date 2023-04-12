package com.danmag.ecommerce.service.model;


import lombok.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@CrossOrigin
@Builder
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    @Valid
    private Brand brand;

    @NotNull
    @Min(0)
    private double price;

    @Size(max = 5000)
    private String description;

    @NotNull
    @Min(0)
    private Integer stock;
    @Valid
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @Valid
    @OneToMany(mappedBy = "product", orphanRemoval = true)
    private List<ProductFeatures> features;


}
