package com.example.featuretoggle.model.requestmodel;

import com.google.gson.JsonElement;
import lombok.Data;

// import javax.validation.constraints.NotNull;

@Data
public class VariationCreationRequestModel {

    private String variationName;

    // @NotNull
    private JsonElement variationValue;

    private boolean targetOffVariation;

    private boolean targetOnDefaultVariation;
}
