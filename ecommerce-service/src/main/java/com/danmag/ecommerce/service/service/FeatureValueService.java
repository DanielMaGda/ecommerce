package com.danmag.ecommerce.service.service;

import com.danmag.ecommerce.service.repository.FeatureValueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeatureValueService {
    @Autowired
    private FeatureValueRepository featureValueRepository;

}
