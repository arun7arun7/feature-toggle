package com.example.featuretoggle.service;

import com.example.featuretoggle.entity.Feature;
import com.example.featuretoggle.model.requestmodel.FeatureEntityDepedenceRequestModel;

import java.util.Set;

public interface FeatureEntityDependenceService {
    Feature create(Feature feature, Set<FeatureEntityDepedenceRequestModel> featureEntityDepedenceRequestModelSet);
}
