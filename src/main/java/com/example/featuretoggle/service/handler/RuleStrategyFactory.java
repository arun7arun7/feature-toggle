package com.example.featuretoggle.service.handler;

import com.example.featuretoggle.constant.ErrorCode;
import com.example.featuretoggle.constant.RuleEvaluationStrategy;
import com.example.featuretoggle.exception.ApiException;
import com.example.featuretoggle.service.RuleEvaluationStrategyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class RuleStrategyFactory {

    @Autowired
    private List<RuleEvaluationStrategyService> featureStrategies;

    private Map<RuleEvaluationStrategy, RuleEvaluationStrategyService> bag = new HashMap<>();

    @PostConstruct
    private void init() {
        for(RuleEvaluationStrategyService ruleEvaluationStrategyService : featureStrategies) {
            bag.put(ruleEvaluationStrategyService.getFeatureEvaluationStrategy(), ruleEvaluationStrategyService);
        }
    }

    public RuleEvaluationStrategyService getService(RuleEvaluationStrategy strategy) throws ApiException {
        if(bag.containsKey(strategy)) {
            return bag.get(strategy);
        }
        else {
            throw new ApiException(ErrorCode.UNKNOWN_STRATEGY_FOR_FEATURE_EVALUATION);
        }
    }

}
