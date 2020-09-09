package com.example.featuretoggle.repository.primary;

import com.example.featuretoggle.entity.Feature;
import org.springframework.stereotype.Repository;

@Repository
public interface FeatureRepository extends BaseRepository<Feature, Integer> {

    Feature findByName(String name);

    Feature findByKey(String key);

    Feature findByKeyAndEnv_Name(String featureKey, String env);

    Feature findByNameAndEnv_Name(String featureName, String env);

}
