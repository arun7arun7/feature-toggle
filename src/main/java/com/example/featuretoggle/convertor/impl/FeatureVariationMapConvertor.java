package com.example.featuretoggle.convertor.impl;

import com.example.featuretoggle.convertor.Convertor;
import com.example.featuretoggle.entity.FeatureVariationMap;
import com.example.featuretoggle.model.Dto.FeatureVariationMapDto;
import org.springframework.stereotype.Service;

@Service
public class FeatureVariationMapConvertor implements Convertor<FeatureVariationMap, FeatureVariationMapDto> {
    @Override
    public FeatureVariationMapDto convertToModel(FeatureVariationMap entity) {
        return null;
    }

    @Override
    public FeatureVariationMap convertToEntity(FeatureVariationMapDto model) {
        return null;
    }
}
