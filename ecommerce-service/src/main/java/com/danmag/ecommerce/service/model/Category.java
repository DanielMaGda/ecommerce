package com.danmag.ecommerce.service.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "parent_category_id")
    private Category parentCategory;

    @ManyToOne
    @JoinColumn(name = "parent_group_id")
    private CategoryGroup parentGroup;

    @OneToMany(mappedBy = "parentCategory")
    private List<Category> subcategories;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "category_feature",
            joinColumns = {@JoinColumn(name = "category_id")},
            inverseJoinColumns = {@JoinColumn(name = "feature_id")})
    private List<Feature> feature;

    public Category() {
        feature = new ArrayList<>();
    }

}
