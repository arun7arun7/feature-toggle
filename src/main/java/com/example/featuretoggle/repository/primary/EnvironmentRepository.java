package com.example.featuretoggle.repository.primary;

import com.example.featuretoggle.entity.Environment;
import org.springframework.stereotype.Repository;

@Repository
public interface EnvironmentRepository extends BaseRepository<Environment, Integer> {

    Environment findByName(String name);

    Environment findByKey(String key);

}
