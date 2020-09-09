package com.example.featuretoggle.model.Dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FeatureEntityDependenceDto {
    private int id;
    private int featureId;
    private String entityKey;
    private String description;
}
