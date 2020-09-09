package com.example.featuretoggle.model;

import com.google.gson.JsonElement;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class Template implements Serializable {

    private JsonElement serve;

    private List<Rule> rules;

}
