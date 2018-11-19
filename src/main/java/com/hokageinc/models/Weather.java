package com.hokageinc.models;

public class Weather {
    private int id;
    private String name;
    private float craterReductionFactor;

    public Weather(int id, String name, float craterReductionFactor) {
        this.id = id;
        this.name = name;
        this.craterReductionFactor = craterReductionFactor;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getCraterReductionFactor() {
        return craterReductionFactor;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", craterReductionFactor=" + craterReductionFactor +
                '}';
    }
}
