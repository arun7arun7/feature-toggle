package com.example.featuretoggle.service;

import com.example.featuretoggle.entity.Feature;
import com.example.featuretoggle.model.requestmodel.VariationCreationRequestModel;

import java.util.Set;


public interface VariationService {

    Feature createVariation(Feature feature, Set<VariationCreationRequestModel> variationCreationRequestModels);


}
