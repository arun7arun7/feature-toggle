package com.example.featuretoggle.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.example.featuretoggle.convertor.impl.FeatureConvertor;
import com.example.featuretoggle.exception.ApiException;
import com.example.featuretoggle.model.ApiResponse;
import com.example.featuretoggle.model.Dto.FeatureDto;
import com.example.featuretoggle.model.requestmodel.FeatureCreationRequestModel;
import com.example.featuretoggle.service.FeatureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/feature")
@Slf4j
public class FeatureController {

    @Autowired
    private FeatureService featureService;

    @Autowired
    private FeatureConvertor featureConvertor;

    @GetMapping("/getStatus/{env}/{key}")
    public ApiResponse<JsonElement> isFeatureEnabled(@PathVariable("key") String featureKey, @PathVariable(name = "env") String env, @RequestBody(required = false) JsonObject evaluationInput) throws ApiException {
        return new ApiResponse<>(featureService.evaluateFeature(featureKey, env, evaluationInput));
    }

    @PostMapping("/create")
    @Transactional
    public ApiResponse<FeatureDto> createFeature(@RequestBody FeatureCreationRequestModel featureCreationRequestModel) throws ApiException {
        return new ApiResponse<>(featureConvertor.convertToModel(featureService.create(featureCreationRequestModel)));
    }

    @PostMapping("/saveEvaluationRules/{featureId}")
    @Transactional
    public ApiResponse<FeatureDto> saveEvaluationRules(@PathVariable("featureId") int featureId, @RequestBody JsonElement evaluationRules) {
        return new ApiResponse<>(featureConvertor.convertToModel(featureService.saveEvaluationRules(featureId, evaluationRules)));
    }

    @PostMapping("/updateQuery/{featureId}")
    @Transactional
    public ApiResponse<FeatureDto> updateQuery(@PathVariable("featureId") int featureId, @RequestBody String query) {
        return new ApiResponse<>(featureConvertor.convertToModel(featureService.updateQuery(featureId, query)));
    }

    @PostMapping("/enable/{featureId}")
    @Transactional
    public ApiResponse<FeatureDto> enableFeature(@PathVariable("featureId") int featureId, @RequestParam("enable") boolean enable) {
        return new ApiResponse<>(featureConvertor.convertToModel(featureService.enableFeature(featureId, enable)));
    }
}
