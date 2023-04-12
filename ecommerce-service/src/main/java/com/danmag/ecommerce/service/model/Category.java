package com.danmag.ecommerce.service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    @Size(min = 2, max = 50)
    @Pattern(regexp = "^[A-Za-z0-9 /]+$")
    private String name;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "parent_category_id")
    private Category parentCategory;
    @JsonIgnore

    @ManyToOne
    @JoinColumn(name = "parent_group_id")
    private CategoryGroup parentGroup;
    @JsonIgnore

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

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parentCategory=" + parentCategory +
                ", parentGroup=" + parentGroup +
                ", feature=" + feature +
                '}';
    }
}
