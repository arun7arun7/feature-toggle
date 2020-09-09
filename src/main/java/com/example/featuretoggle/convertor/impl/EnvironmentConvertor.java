package com.example.featuretoggle.convertor.impl;

import com.example.featuretoggle.convertor.Convertor;
import com.example.featuretoggle.entity.Environment;
import com.example.featuretoggle.model.Dto.EnvironmentDto;
import org.springframework.stereotype.Service;

@Service
public class EnvironmentConvertor implements Convertor<Environment, EnvironmentDto> {
    @Override
    public EnvironmentDto convertToModel(Environment entity) {
        return EnvironmentDto.builder().id(entity.getId()).name(entity.getName()).key(entity.getKey()).build();
    }

    @Override
    public Environment convertToEntity(EnvironmentDto model) {
        if(model == null) return null;
        Environment environment = new Environment();
        environment.setId(model.getId());
        return environment;
    }
}
