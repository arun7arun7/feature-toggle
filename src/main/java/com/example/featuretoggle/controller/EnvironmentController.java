package com.example.featuretoggle.controller;

import com.example.featuretoggle.convertor.impl.EnvironmentConvertor;
import com.example.featuretoggle.model.ApiResponse;
import com.example.featuretoggle.model.Dto.EnvironmentDto;
import com.example.featuretoggle.service.EnvironmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/environment")
@Slf4j
public class EnvironmentController {

    @Autowired
    private EnvironmentService environmentService;

    @Autowired
    private EnvironmentConvertor environmentConvertor;

    @PostMapping("/add")
    @Transactional
    public ApiResponse<EnvironmentDto> addEnvironment(@RequestParam("name") String name, @RequestParam("key") String key) {
        return new ApiResponse<>(environmentConvertor.convertToModel(environmentService.create(name, key)));
    }

    @GetMapping("/get")
    public ApiResponse<Collection<EnvironmentDto>> findAll() {
        return new ApiResponse<>(environmentConvertor.convertToModel(environmentService.findAll()));
    }
}
