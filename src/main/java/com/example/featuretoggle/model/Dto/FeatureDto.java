package com.example.featuretoggle.model.Dto;

import com.google.gson.JsonElement;
import com.example.featuretoggle.constant.VariationType;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Collection;

@Data
@Builder
public class FeatureDto implements Serializable {

    private int id;

    private String name;

    private String key;

    private EnvironmentDto env;

    private VariationType variationType;

    private String description;

    private String query;

    private boolean enabled;

    private JsonElement evaluationRules;

    private Collection<VariationDto> variations;

    private VariationDto targetOffVariation;

    private VariationDto targetOnDefaultVariation;

    private Collection<FeatureEntityDependenceDto> featureEntityDependenceDtos;

}
