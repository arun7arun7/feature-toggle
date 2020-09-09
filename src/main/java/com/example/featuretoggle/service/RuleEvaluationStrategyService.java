package com.example.featuretoggle.service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.example.featuretoggle.constant.RuleEvaluationStrategy;
import com.google.gson.JsonObject;

public interface RuleEvaluationStrategyService<T> {

    boolean isConditionSatisfy(T ruleData, JsonObject evaluationInput);

    default boolean isConditionSatisfy(JsonElement ruleData, JsonObject evaluationInput) {
        Gson gson = new Gson();
        T ruleDataClass = gson.fromJson(ruleData, getDataClass());
        return isConditionSatisfy(ruleDataClass, evaluationInput);
    }

    RuleEvaluationStrategy getFeatureEvaluationStrategy();

    Class<T> getDataClass();
}
