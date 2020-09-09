package com.example.featuretoggle.service.impl;

import com.example.featuretoggle.entity.Feature;
import com.example.featuretoggle.entity.FeatureEntityDependence;
import com.example.featuretoggle.model.requestmodel.FeatureEntityDepedenceRequestModel;
import com.example.featuretoggle.repository.primary.FeatureEntityDependenceRepository;
import com.example.featuretoggle.service.FeatureEntityDependenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class FeatureEntityDependenceServiceImpl implements FeatureEntityDependenceService {

    @Autowired
    private FeatureEntityDependenceRepository featureEntityDependenceRepository;

    @Override
    @Transactional
    public Feature create(Feature feature, Set<FeatureEntityDepedenceRequestModel> featureEntityDepedenceRequestModelSet) {
        List<FeatureEntityDependence> featureEntityDependences = new ArrayList<>();
        for (FeatureEntityDepedenceRequestModel featureEntityDepedenceRequestModel : featureEntityDepedenceRequestModelSet) {
            FeatureEntityDependence featureEntityDependence = new FeatureEntityDependence();
            featureEntityDependence.setFeature(feature);
            featureEntityDependence.setEntityKey(featureEntityDepedenceRequestModel.getEntityKey());
            featureEntityDependence.setDescription(featureEntityDepedenceRequestModel.getDescription());
            featureEntityDependences.add(featureEntityDependence);
        }
        for (FeatureEntityDependence featureEntityDependence : featureEntityDependences) {
            feature.addFeatureEntityDepedence(featureEntityDependence);
        }
        return feature;
    }
}
