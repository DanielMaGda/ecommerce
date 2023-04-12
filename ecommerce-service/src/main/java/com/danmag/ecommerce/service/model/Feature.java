package com.danmag.ecommerce.service.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@AllArgsConstructor
@Builder
@Table(name = "feature")
public class Feature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Size(min = 2, max = 50)
    @Pattern(regexp = "^[A-Za-z0-9\\s]+$")
    private String fullName;

    @NotNull
    @Size(min = 2, max = 30)
    @Pattern(regexp = "^[A-Za-z0-9\\s]+$")
    private String shortName;

    @Size(max = 255)
    @Pattern(regexp = "^[A-Za-z0-9\\s]+$")
    private String description;

    @Valid
    @OneToMany(mappedBy = "feature", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<ProductFeatures> productFeatures = new HashSet<>();


}
