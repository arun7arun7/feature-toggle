package com.example.featuretoggle.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.example.featuretoggle.entity.Feature;
import com.example.featuretoggle.exception.ApiException;
import com.example.featuretoggle.model.requestmodel.FeatureCreationRequestModel;


public interface FeatureService {

    Feature getFeatureByName(String featureName);

    Feature getFeatureByKey(String featureKey);

    Feature create(FeatureCreationRequestModel featureCreationRequestModel) throws ApiException;

    Feature findByFeatureKeyAndEnv(String featureKey, String env);

    JsonElement evaluateFeature(String featureKey, String env, JsonObject evaluationInput);

    Feature saveEvaluationRules(int featureId, JsonElement evaluationRules);

    Feature updateQuery(int featureId, String query);

    Feature enableFeature(int featureId, boolean enable);
}
