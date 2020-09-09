package com.example.featuretoggle.service;

import com.example.featuretoggle.model.Template;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public interface TemplateService {

    JsonElement evaluateTemplates(Template[] templates, JsonObject evaluationInput);

    JsonElement evaluateTemplate(Template template, JsonObject evaluationInput);
}
