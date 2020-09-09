package com.example.featuretoggle.service.impl;

import com.example.featuretoggle.convertor.impl.FeatureVariationMapConvertor;
import com.example.featuretoggle.entity.FeatureVariationMap;
import com.example.featuretoggle.repository.primary.FeatureVariationMapRepository;
import com.example.featuretoggle.service.FeatureVariationMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class FeatureVariationMapServiceImpl implements FeatureVariationMapService {

    @Autowired
    private FeatureVariationMapRepository featureVariationMapRepository;

    @Autowired
    private FeatureVariationMapConvertor featureVariationMapConvertor;

    @Override
    @Transactional
    public FeatureVariationMap create() {
        return new FeatureVariationMap();
    }
}
