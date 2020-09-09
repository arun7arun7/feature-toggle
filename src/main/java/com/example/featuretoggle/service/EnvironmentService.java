package com.example.featuretoggle.service;

import com.example.featuretoggle.entity.Environment;

import java.util.List;


public interface EnvironmentService {

    Environment getByName(String name);

    Environment create(String name, String key);

    List<Environment> findAll();
}
