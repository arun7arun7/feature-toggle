package com.example.featuretoggle.model.Dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnvironmentDto {
    private int id;
    private String name;
    private String key;
}
