package com.danmag.ecommerce.service.repository;

import com.danmag.ecommerce.service.model.ProductFeatures;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductFeatureRepository extends JpaRepository<ProductFeatures, Long> {

    List<ProductFeatures> findByProductId(long productId);

}
