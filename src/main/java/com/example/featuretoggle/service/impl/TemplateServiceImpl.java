package com.example.featuretoggle.service.impl;

import com.example.featuretoggle.model.Rule;
import com.example.featuretoggle.model.Template;
import com.example.featuretoggle.service.TemplateService;
import com.example.featuretoggle.service.handler.RuleStrategyFactory;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TemplateServiceImpl implements TemplateService {

    @Autowired
    private RuleStrategyFactory ruleStrategyFactory;

    @Override
    public JsonElement evaluateTemplates(Template[] templates, JsonObject evaluationInput) {
        for(Template template : templates) {
            JsonElement serve = evaluateTemplate(template, evaluationInput);
            if(serve != null) {
                return serve;
            }
        }
        return null;
    }

    @Override
    public JsonElement evaluateTemplate(Template template, JsonObject evaluationInput) {
        List<Rule> ruleList = template.getRules();
        for(Rule rule : ruleList) {
            boolean conditionSatisfy = ruleStrategyFactory.getService(rule.getRuleEvaluationStrategy()).isConditionSatisfy(rule.getData(), evaluationInput);
            if(!conditionSatisfy) {
                return null;
            }
        }
        return template.getServe();
    }

}
