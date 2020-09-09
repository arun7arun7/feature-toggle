package com.example.featuretoggle.model;


import com.google.gson.JsonElement;
import com.example.featuretoggle.constant.RuleEvaluationStrategy;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class Rule implements Serializable {

    private RuleEvaluationStrategy ruleEvaluationStrategy;

    private JsonElement data;

}
