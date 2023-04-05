package com.danmag.ecommerce.service.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
@CrossOrigin
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Size(min = 1, max = 255)
    private String name;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    @Valid
    private Brand brand;

    @NotNull
    @Min(value = 0)
    private Long price;

    @Size(max = 5000)
    private String description;

    @NotNull
    @Min(value = 0)
    private Integer stock;
    @Valid
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @Valid
    @OneToMany(mappedBy = "product", orphanRemoval = true)
    private List<ProductFeatures> features;


}
