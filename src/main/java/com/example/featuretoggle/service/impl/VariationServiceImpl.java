package com.example.featuretoggle.service.impl;

import com.google.gson.Gson;
import com.example.featuretoggle.convertor.impl.VariationConvertor;
import com.example.featuretoggle.entity.Feature;
import com.example.featuretoggle.entity.FeatureVariationMap;
import com.example.featuretoggle.entity.Variation;
import com.example.featuretoggle.model.requestmodel.VariationCreationRequestModel;
import com.example.featuretoggle.repository.primary.VariationRepository;
import com.example.featuretoggle.service.FeatureVariationMapService;
import com.example.featuretoggle.service.VariationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class VariationServiceImpl implements VariationService {

    @Autowired
    private VariationRepository variationRepository;

    @Autowired
    private VariationConvertor variationConvertor;

    @Autowired
    private FeatureVariationMapService featureVariationMapService;

    @Autowired
    private Gson gson;

    @Override
    @Transactional
    public Feature createVariation(Feature feature, Set<VariationCreationRequestModel> variationCreationRequestModels) {
        List<Variation> variations = new ArrayList<>();
        FeatureVariationMap featureVariationMap = featureVariationMapService.create();
        feature.setFeatureVariationMap(featureVariationMap);
        for(VariationCreationRequestModel variationCreationRequestModel : variationCreationRequestModels) {
            Variation variation = new Variation();
            variation.setName(variationCreationRequestModel.getVariationName());
            variation.setValue(gson.toJson(variationCreationRequestModel.getVariationValue()));
            if (variationCreationRequestModel.isTargetOnDefaultVariation()) {
                variation.setTargetOnDefaultVariationMap(featureVariationMap);
            }
            if (variationCreationRequestModel.isTargetOffVariation()) {
                variation.setTargetOffVariationMap(featureVariationMap);
            }
            variations.add(variation);
        }
        for (Variation variation : variations) {
            feature.addVariation(variation);
        }
        return feature;
    }
}
