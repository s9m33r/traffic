package com.hokageinc.models;

public class Weather {
    private String name;
    private int craterReductionFactor;

    public Weather(String name, int craterReductionFactor){
        this.name = name;
        this.craterReductionFactor = craterReductionFactor;
    }

    public String getName() {
        return name;
    }

    public int getCraterReductionFactor(){
        return craterReductionFactor;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "name='" + name + '\'' +
                ", craterReductionFactor=" + craterReductionFactor +
                '}';
    }
}
