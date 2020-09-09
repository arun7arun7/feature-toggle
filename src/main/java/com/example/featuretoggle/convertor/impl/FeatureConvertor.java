package com.example.featuretoggle.convertor.impl;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.example.featuretoggle.convertor.Convertor;
import com.example.featuretoggle.entity.Feature;
import com.example.featuretoggle.model.Dto.FeatureDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeatureConvertor implements Convertor<Feature, FeatureDto> {

    @Autowired
    private Gson gson;

    @Autowired
    private VariationConvertor variationConvertor;

    @Autowired
    private FeatureEntityDependenceConvertor featureEntityDependenceConvertor;

    @Autowired
    private EnvironmentConvertor environmentConvertor;

    @Override
    public FeatureDto convertToModel(Feature entity) {
        if(entity == null) return null;
        return FeatureDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .key(entity.getKey())
                .description(entity.getDescription())
                .enabled(entity.isEnabled())
                .env(environmentConvertor.convertToModel(entity.getEnv()))
                .evaluationRules(gson.fromJson(entity.getEvaluationRules(), JsonElement.class))
                .variations(variationConvertor.convertToModel(entity.getVariations()))
                .targetOffVariation(variationConvertor.convertToModel(entity.getFeatureVariationMap().getTargetOffVariation()))
                .targetOnDefaultVariation(variationConvertor.convertToModel(entity.getFeatureVariationMap().getTargetOnDefaultVariation()))
                .variationType(entity.getVariationType())
                .query(entity.getQueryString())
                .featureEntityDependenceDtos(featureEntityDependenceConvertor.convertToModel(entity.getFeatureEntityDependences()))
                .build();
    }

    @Override
    public Feature convertToEntity(FeatureDto model) {
        if(model == null) return null;
        Feature feature = new Feature();
        feature.setId(model.getId());
        return feature;
    }

}
