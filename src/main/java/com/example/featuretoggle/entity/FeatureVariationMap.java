package com.example.featuretoggle.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "feature_variation_map")
public class FeatureVariationMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "feature_id")
    private Feature feature;

    @OneToOne
    @JoinColumn(name = "target_off_variation_id")
    private Variation targetOffVariation;

    @OneToOne
    @JoinColumn(name = "target_on_default_variation_id")
    private Variation targetOnDefaultVariation;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Feature getFeature() {
        return feature;
    }

    public void setFeature(Feature feature) {
        this.feature = feature;
    }

    public Variation getTargetOffVariation() {
        return targetOffVariation;
    }

    public void setTargetOffVariation(Variation targetOffVariation) {
        this.targetOffVariation = targetOffVariation;
    }

    public Variation getTargetOnDefaultVariation() {
        return targetOnDefaultVariation;
    }

    public void setTargetOnDefaultVariation(Variation targetOnDefaultVariation) {
        this.targetOnDefaultVariation = targetOnDefaultVariation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FeatureVariationMap)) return false;
        FeatureVariationMap that = (FeatureVariationMap) o;
        return getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
