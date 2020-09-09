package com.example.featuretoggle.model;

import com.google.gson.JsonElement;
import com.example.featuretoggle.constant.CustomAttributesOperator;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class CustomAttributeEvaluationModel implements Serializable {

    private String key;

    private CustomAttributesOperator customAttributesOperator;

    private JsonElement values;
}
