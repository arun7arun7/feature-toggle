package com.example.featuretoggle.model.Dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FeatureVariationMapDto {

    private int featureId;

    private int targetOffVariationId;

    private int targetOnDefaultVariationId;
}
