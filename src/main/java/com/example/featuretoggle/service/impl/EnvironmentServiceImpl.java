package com.example.featuretoggle.service.impl;

import com.example.featuretoggle.constant.ErrorCode;
import com.example.featuretoggle.entity.Environment;
import com.example.featuretoggle.exception.ApiException;
import com.example.featuretoggle.repository.primary.EnvironmentRepository;
import com.example.featuretoggle.service.EnvironmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnvironmentServiceImpl implements EnvironmentService {

    @Autowired
    private EnvironmentRepository environmentRepository;

    @Override
    public Environment getByName(String name) {
        return environmentRepository.findByName(name);
    }

    @Override
    public Environment create(String name, String key) {
        validateAddEnvironment(name, key);
        Environment environment = new Environment();
        environment.setKey(key);
        environment.setName(name);
        return environmentRepository.save(environment);
    }

    private void validateAddEnvironment(String name, String key) {
        Environment environment = environmentRepository.findByName(name);
        if (environment != null) {
            throw new ApiException(ErrorCode.ENVIRONMENT_NAME_ALREADY_EXISTS);
        }
        environment = environmentRepository.findByKey(key);
        if (environment != null) {
            throw new ApiException(ErrorCode.ENVIRONMENT_KEY_ALREADY_EXISTS);
        }
    }

    @Override
    public List<Environment> findAll() {
        return environmentRepository.findAll();
    }
}
