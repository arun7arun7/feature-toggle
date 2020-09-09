package com.example.featuretoggle.entity;

import com.example.featuretoggle.constant.VariationType;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "feature")
public class Feature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "feature_name")
    private String name;

    @Column(name = "feature_key")
    private String key;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "environment_id")
    private Environment env;

    @Column(name = "variation_type")
    @Enumerated(EnumType.STRING)
    private VariationType variationType;

    @OneToMany( fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "feature")
    private List<Variation> variations = new ArrayList<>();

    @OneToMany( fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "feature")
    private List<FeatureEntityDependence> featureEntityDependences = new ArrayList<>();

    @Column(name = "query_string")
    private String queryString;

    @Column(name = "description")
    private String description;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "evaluation_rules", columnDefinition = "json")
    private String evaluationRules;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "feature")
    private FeatureVariationMap featureVariationMap;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Environment getEnv() {
        return env;
    }

    public void setEnv(Environment env) {
        this.env = env;
    }

    public VariationType getVariationType() {
        return variationType;
    }

    public void setVariationType(VariationType variationType) {
        this.variationType = variationType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getEvaluationRules() {
        return evaluationRules;
    }

    public void setEvaluationRules(String evaluationRules) {
        this.evaluationRules = evaluationRules;
    }

    public FeatureVariationMap getFeatureVariationMap() {
        return featureVariationMap;
    }

    public void setFeatureVariationMap(FeatureVariationMap featureVariationMap) {
        if (featureVariationMap == null) {
            if (this.featureVariationMap != null) {
                this.featureVariationMap.setFeature(null);
            }
        }
        else {
            featureVariationMap.setFeature(this);
        }
        this.featureVariationMap = featureVariationMap;
    }

    public List<Variation> getVariations() {
        return variations;
    }

    public void addVariation(Variation variation) {
        variations.add(variation);
        variation.setFeature(this);
    }

    public List<FeatureEntityDependence> getFeatureEntityDependences() {
        return featureEntityDependences;
    }

    public void addFeatureEntityDepedence(FeatureEntityDependence featureEntityDependence) {
        featureEntityDependences.add(featureEntityDependence);
        featureEntityDependence.setFeature(this);
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Feature)) return false;
        Feature feature = (Feature) o;
        return getId() == feature.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
