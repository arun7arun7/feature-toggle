package com.example.featuretoggle.model.requestmodel;

import com.example.featuretoggle.constant.VariationType;
import lombok.Data;

// import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class FeatureCreationRequestModel {

    // @NotNull
    private String featureName;

    // @NotNull
    private String featureKey;

    // @NotNull
    private String env;

    private String description;

    // @NotNull
    private VariationType variationType;

    // @NotNull
    private Set<VariationCreationRequestModel> variations;

    private Set<FeatureEntityDepedenceRequestModel> featureEntityDependenceRequestModels;

    private String queryString;

}
