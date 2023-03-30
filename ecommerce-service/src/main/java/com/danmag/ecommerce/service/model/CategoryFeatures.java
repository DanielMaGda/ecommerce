package com.danmag.ecommerce.service.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "category_feature")
public class CategoryFeatures {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feature_id")
    private Feature feature;

}
