package com.example.featuretoggle.convertor.impl;

import com.example.featuretoggle.convertor.Convertor;
import com.example.featuretoggle.entity.FeatureEntityDependence;
import com.example.featuretoggle.model.Dto.FeatureEntityDependenceDto;
import org.springframework.stereotype.Service;

@Service
public class FeatureEntityDependenceConvertor implements Convertor<FeatureEntityDependence, FeatureEntityDependenceDto> {
    @Override
    public FeatureEntityDependenceDto convertToModel(FeatureEntityDependence entity) {
        return FeatureEntityDependenceDto
                .builder()
                .id(entity.getId())
                .featureId(entity.getFeature().getId())
                .entityKey(entity.getEntityKey())
                .description(entity.getDescription())
                .build();
    }

    @Override
    public FeatureEntityDependence convertToEntity(FeatureEntityDependenceDto model) {
        if(model == null) return null;
        FeatureEntityDependence featureEntityDependence = new FeatureEntityDependence();
        featureEntityDependence.setId(model.getId());
        return featureEntityDependence;
    }
}
