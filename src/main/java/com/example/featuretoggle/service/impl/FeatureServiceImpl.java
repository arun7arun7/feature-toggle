package com.example.featuretoggle.service.impl;

import com.example.featuretoggle.service.*;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.example.featuretoggle.constant.ErrorCode;
import com.example.featuretoggle.entity.Feature;
import com.example.featuretoggle.entity.FeatureEntityDependence;
import com.example.featuretoggle.exception.ApiException;
import com.example.featuretoggle.model.QueryResult;
import com.example.featuretoggle.model.Template;
import com.example.featuretoggle.model.requestmodel.FeatureCreationRequestModel;
import com.example.featuretoggle.repository.primary.FeatureRepository;
import com.example.featuretoggle.repository.secondary.custom.SecondarySourceReadRepository;
import com.google.gson.JsonPrimitive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class FeatureServiceImpl implements FeatureService {

    @Autowired
    private FeatureRepository featureRepository;

    @Autowired
    private TemplateService templateService;

    @Autowired
    private Gson gson;

    @Autowired
    private VariationService variationService;

    @Autowired
    private EnvironmentService environmentService;

    @Autowired
    private FeatureEntityDependenceService featureEntityDependenceService;

    @Autowired
    private SecondarySourceReadRepository secondarySourceReadRepository;



    @Override
    public Feature getFeatureByName(String featureName) {
        return featureRepository.findByName(featureName);
    }

    @Override
    public Feature getFeatureByKey(String featureKey) {
        return featureRepository.findByKey(featureKey);
    }

    @Override
    public Feature findByFeatureKeyAndEnv(String featureKey, String env) {
        return featureRepository.findByKeyAndEnv_Name(featureKey,env);
    }

    @Override
    public JsonElement evaluateFeature(String featureKey, String env, JsonObject evaluationInput) {
        Feature feature = findByFeatureKeyAndEnv(featureKey, env);
        if(feature == null) {
            throw new ApiException(ErrorCode.FEATURE_NOT_FOUND);
        }
        if(!feature.isEnabled()) {
            return gson.fromJson(feature.getFeatureVariationMap().getTargetOffVariation().getValue(), JsonElement.class);
        }
        if(feature.getEvaluationRules() == null || evaluationInput == null) {
            return gson.fromJson(feature.getFeatureVariationMap().getTargetOnDefaultVariation().getValue(), JsonElement.class);
        }
        JsonObject newEvaluationInput;
        try {
            newEvaluationInput = addAttributesFromQuery(feature.getQueryString(), feature.getFeatureEntityDependences(), evaluationInput);
        }
        catch (ApiException e) {
            log.error("Query Execution Error: Returning targetOnDefaultVariation" );
            log.error("FeatureKey : " + featureKey + " env : " + env + " evaluationInput : " + evaluationInput.toString());
            log.error(e.getMessage());
            return gson.fromJson(feature.getFeatureVariationMap().getTargetOnDefaultVariation().getValue(), JsonElement.class);
        }
        Template[] templates = gson.fromJson(feature.getEvaluationRules(), Template[].class);
        JsonElement serveValue = templateService.evaluateTemplates(templates, newEvaluationInput);
        if(serveValue == null) {
            return gson.fromJson(feature.getFeatureVariationMap().getTargetOnDefaultVariation().getValue(), JsonElement.class);
        }
        return serveValue;
    }

    private JsonObject addAttributesFromQuery(String queryString, List<FeatureEntityDependence> featureEntityDependences, JsonObject evaluationInput) {
        if (queryString == null) {
            return evaluationInput;
        }
        Map<String, Object> map = new HashMap<>();
        for (FeatureEntityDependence featureEntityDependence : featureEntityDependences) {
            String entityKey = featureEntityDependence.getEntityKey();
            if (evaluationInput.has(entityKey)) {
                map.put(entityKey, evaluationInput.get(entityKey).toString());
            }
        }
        QueryResult queryResult;
        try {
           queryResult = secondarySourceReadRepository.execute(queryString, map);
        }
        catch (Exception e) {
            throw new ApiException(ErrorCode.QUERY_EXECUTION_ERROR, e.getMessage());
        }
        List<List<Object>> rows = queryResult.getRows();
        List<String> columns = queryResult.getColumns();
        List<Object> row;
        if (rows.size() == 0) {
            throw new ApiException(ErrorCode.NO_ENTRY_FOUND, ErrorCode.NO_ENTRY_FOUND.getErrorMessage());
        }
        else if (rows.size() > 1) {
            log.info("Multiple Entry Found for queryString : " + queryString + " Choosing random entry");
        }
        row = rows.get(0);
        for (int i = 0; i < columns.size(); i++) {
            String columnName = columns.get(i);
            if (!evaluationInput.has(columnName)) {
                evaluationInput.add(columnName, gson.toJsonTree(row.get(i)));
            }
        }
        return evaluationInput;
    }

    private Object getAsObject(JsonElement jsonElement) {
        if (jsonElement.isJsonNull()) {
            return null;
        }

        if (jsonElement.isJsonObject()) {
            return jsonElement.getAsJsonObject();
        }

        if (jsonElement.isJsonPrimitive()) {
            JsonPrimitive jsonPrimitive = jsonElement.getAsJsonPrimitive();
            if (jsonPrimitive.isNumber()) {
                return jsonPrimitive.getAsNumber();
            } else if (jsonPrimitive.isString()) {
                return jsonPrimitive.getAsString();
            } else {
                return jsonPrimitive.getAsBoolean();
            }
        }

        throw new IllegalStateException("Unexpected json element type");
    }

    @Override
    @Transactional
    public Feature create(FeatureCreationRequestModel featureCreationRequestModel) {
        validateFeatureCreationRequestModel(featureCreationRequestModel);
        Feature feature = new Feature();
        feature.setKey(featureCreationRequestModel.getFeatureKey());
        feature.setName(featureCreationRequestModel.getFeatureName());
        feature.setEnv(environmentService.getByName(featureCreationRequestModel.getEnv()));
        feature.setVariationType(featureCreationRequestModel.getVariationType());
        feature.setEnabled(false);
        feature.setDescription(featureCreationRequestModel.getDescription());
        feature.setQueryString(featureCreationRequestModel.getQueryString());
        variationService.createVariation(feature, featureCreationRequestModel.getVariations());
        featureEntityDependenceService.create(feature, featureCreationRequestModel.getFeatureEntityDependenceRequestModels());
        return featureRepository.save(feature);
    }

    private void validateFeatureCreationRequestModel(FeatureCreationRequestModel featureCreationRequestModel) {
        Feature feature = featureRepository.findByKeyAndEnv_Name(featureCreationRequestModel.getFeatureKey(), featureCreationRequestModel.getEnv());
        if(feature != null) {
            throw new ApiException(ErrorCode.FEATURE_KEY_ALREADY_EXISTS);
        }
        feature = featureRepository.findByNameAndEnv_Name(featureCreationRequestModel.getFeatureName(), featureCreationRequestModel.getEnv());
        if(feature != null) {
            throw new ApiException(ErrorCode.FEATURE_NAME_ALREADY_EXISTS);
        }
    }

    @Transactional
    public Feature saveEvaluationRules(int featureId, JsonElement evaluationRules) {
        Feature feature = featureRepository.getOne(featureId);
        feature.setEvaluationRules(gson.toJson(evaluationRules));
        return featureRepository.save(feature);
    }

    @Override
    public Feature updateQuery(int featureId, String query) {
        Feature feature = featureRepository.getOne(featureId);
        feature.setQueryString(query);
        return featureRepository.save(feature);
    }

    @Override
    public Feature enableFeature(int featureId, boolean enable) {
        Feature feature = featureRepository.getOne(featureId);
        feature.setEnabled(enable);
        return featureRepository.save(feature);
    }

}
