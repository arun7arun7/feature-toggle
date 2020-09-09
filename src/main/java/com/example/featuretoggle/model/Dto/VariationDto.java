package com.example.featuretoggle.model.Dto;

import com.google.gson.JsonElement;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VariationDto {

    private int variationId;

    private String name;

    private JsonElement value;
}
