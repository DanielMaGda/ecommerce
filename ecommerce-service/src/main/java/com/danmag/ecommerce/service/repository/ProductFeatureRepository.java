package com.danmag.ecommerce.service.repository;

import com.danmag.ecommerce.service.model.ProductFeatures;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductFeatureRepository extends JpaRepository<ProductFeatures, Long> {

}
