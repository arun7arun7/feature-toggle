package com.example.featuretoggle.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "variation")
public class Variation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feature_id", nullable = false)
    private Feature feature;

    @Column(name = "variation_name")
    private String name;

    @Column(name = "value", columnDefinition = "json")
    private String value;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "targetOffVariation")
    private FeatureVariationMap targetOffVariationMap;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "targetOnDefaultVariation")
    private FeatureVariationMap targetOnDefaultVariationMap;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public FeatureVariationMap getTargetOffVariationMap() {
        return targetOffVariationMap;
    }

    public void setTargetOffVariationMap(FeatureVariationMap targetOffVariationMap) {
        if (targetOffVariationMap == null) {
            if (this.targetOffVariationMap != null) {
                this.targetOffVariationMap.setTargetOffVariation(null);
            }
        }
        else {
            targetOffVariationMap.setTargetOffVariation(this);
        }
        this.targetOffVariationMap = targetOffVariationMap;
    }

    public FeatureVariationMap getTargetOnDefaultVariationMap() {
        return targetOnDefaultVariationMap;
    }

    public void setTargetOnDefaultVariationMap(FeatureVariationMap targetOnDefaultVariationMap) {
        if (targetOnDefaultVariationMap == null) {
            if (this.targetOnDefaultVariationMap != null) {
                this.targetOnDefaultVariationMap.setTargetOnDefaultVariation(null);
            }
        }
        else {
            targetOnDefaultVariationMap.setTargetOnDefaultVariation(this);
        }
        this.targetOnDefaultVariationMap = targetOnDefaultVariationMap;
    }

    public Feature getFeature() {
        return feature;
    }

    public void setFeature(Feature feature) {
        this.feature = feature;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Variation)) return false;
        Variation variation = (Variation) o;
        return getId() == variation.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }


}
