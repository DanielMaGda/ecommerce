package com.danmag.ecommerce.service.repository;

import com.danmag.ecommerce.service.model.FeatureValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeatureValueRepository extends JpaRepository<FeatureValue, Long> {

}
