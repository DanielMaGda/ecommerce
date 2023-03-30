package com.danmag.ecommerce.service.service;

import com.danmag.ecommerce.service.repository.FeatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeatureService {
    @Autowired
    private FeatureRepository featureRepository;


}
