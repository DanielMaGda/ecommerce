package com.danmag.ecommerce.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "feature_values")
public class FeatureValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Size(min = 2, max = 100)
    @Pattern(regexp = "^[A-Za-z0-9\\s]+$")
    private String fullName;

    @NotNull
    @Size(min = 2, max = 30)
    @Pattern(regexp = "^[A-Za-z0-9\\s\\-]+$")
    private String shortName;

    @Size(max = 255)
    @Pattern(regexp = "^[A-Za-z0-9\\s]+$")
    private String description;

    @NotNull
    @Valid
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feature_id")
    private Feature feature;

    @Valid
    @OneToMany(mappedBy = "featureValue", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<ProductFeatures> productFeatures = new HashSet<>();


}
