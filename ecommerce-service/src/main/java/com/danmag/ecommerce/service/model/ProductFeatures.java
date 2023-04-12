package com.danmag.ecommerce.service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "product_features")
public class ProductFeatures {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feature_id")
    private Feature feature;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feature_values_id", nullable = false)
    private FeatureValue featureValue;


    public ProductFeatures(ProductFeatures productFeatures) {
        this.product = productFeatures.getProduct();
        this.feature = productFeatures.getFeature();
        this.featureValue = productFeatures.getFeatureValue();
    }


}
