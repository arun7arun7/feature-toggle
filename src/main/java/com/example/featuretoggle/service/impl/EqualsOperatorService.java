package com.example.featuretoggle.service.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.example.featuretoggle.constant.CustomAttributesOperator;
import com.example.featuretoggle.service.CustomAttributesOperatorService;
import org.springframework.stereotype.Service;

@Service
public class EqualsOperatorService implements CustomAttributesOperatorService {
    @Override
    public boolean evaluate(JsonElement attributeValue, JsonElement dbValue) {
        if(attributeValue.isJsonPrimitive()) {
            return evaluate(attributeValue.getAsJsonPrimitive(), dbValue.getAsJsonArray());
        }
        return false;
    }

    private boolean evaluate(JsonPrimitive attributeValue, JsonArray dbValue) {
        for(JsonElement jsonElement : dbValue) {
            if(jsonElement.isJsonPrimitive() && attributeValue.equals(jsonElement.getAsJsonPrimitive())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public CustomAttributesOperator getCustomAttributesOperation() {
        return CustomAttributesOperator.EQUALS;
    }
}
