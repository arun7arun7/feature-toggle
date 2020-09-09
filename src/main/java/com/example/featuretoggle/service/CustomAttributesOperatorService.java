package com.example.featuretoggle.service;

import com.google.gson.JsonElement;
import com.example.featuretoggle.constant.CustomAttributesOperator;


public interface CustomAttributesOperatorService {

    boolean evaluate(JsonElement attributeValue, JsonElement dbValue);

    CustomAttributesOperator getCustomAttributesOperation();
}
