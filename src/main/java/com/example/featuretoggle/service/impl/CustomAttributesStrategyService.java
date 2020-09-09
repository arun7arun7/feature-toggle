package com.example.featuretoggle.service.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.example.featuretoggle.constant.RuleEvaluationStrategy;
import com.example.featuretoggle.model.CustomAttributeEvaluationModel;
import com.example.featuretoggle.service.RuleEvaluationStrategyService;
import com.example.featuretoggle.service.handler.CustomOperatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomAttributesStrategyService implements RuleEvaluationStrategyService<CustomAttributeEvaluationModel> {

    @Autowired
    private CustomOperatorFactory customOperatorFactory;

    @Override
    public boolean isConditionSatisfy(CustomAttributeEvaluationModel data, JsonObject evaluationInput) {
        if(!evaluationInput.has(data.getKey())) {
            return false;
        }
        JsonElement customAttributeValue = evaluationInput.get(data.getKey());
        return customOperatorFactory.getService(data.getCustomAttributesOperator()).evaluate(customAttributeValue, data.getValues());
    }

    @Override
    public RuleEvaluationStrategy getFeatureEvaluationStrategy() {
        return RuleEvaluationStrategy.CUSTOM_ATTRIBUTE;
    }

    @Override
    public Class<CustomAttributeEvaluationModel> getDataClass() {
        return CustomAttributeEvaluationModel.class;
    }

}
