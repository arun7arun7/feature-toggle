package com.example.featuretoggle.convertor.impl;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.example.featuretoggle.convertor.Convertor;
import com.example.featuretoggle.entity.Variation;
import com.example.featuretoggle.model.Dto.VariationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VariationConvertor implements Convertor<Variation, VariationDto> {

    @Autowired
    private Gson gson;

    @Override
    public VariationDto convertToModel(Variation entity) {

        return VariationDto.builder()
                .name(entity.getName())
                .value(gson.fromJson(entity.getValue(), JsonElement.class))
                .variationId(entity.getId())
                .build();
    }

    @Override
    public Variation convertToEntity(VariationDto model) {
        return null;
    }
}
