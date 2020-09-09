package com.example.featuretoggle.service.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.example.featuretoggle.constant.CustomAttributesOperator;
import com.example.featuretoggle.service.CustomAttributesOperatorService;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class RegexOperatorService implements CustomAttributesOperatorService {
    @Override
    public boolean evaluate(JsonElement attributeValue, JsonElement dbValue) {
        if(attributeValue.isJsonPrimitive()) {
            JsonPrimitive attributeValuePrimitive = attributeValue.getAsJsonPrimitive();
            if(attributeValuePrimitive.isString()) {
                String input = attributeValuePrimitive.getAsString();
                String regex = dbValue.getAsString();
                return Pattern.matches(regex, input);
            }
        }
        return false;
    }

    @Override
    public CustomAttributesOperator getCustomAttributesOperation() {
        return CustomAttributesOperator.REGEX;
    }
}
