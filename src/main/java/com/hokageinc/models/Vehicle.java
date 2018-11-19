package com.hokageinc.models;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Vehicle {
    private int id;
    private String name;
    private float topSpeed;
    private float timeRequiredToCrossACrater;
    private Set<Weather> preferredWeathers = new HashSet<>();

    public Vehicle(int id, String name, float topSpeed, float timeRequiredToCrossACrater, Weather... preferredWeathers) {
        this.id = id;
        this.name = name;
        this.topSpeed = topSpeed;
        this.timeRequiredToCrossACrater = timeRequiredToCrossACrater / 60;
        this.preferredWeathers.addAll(Arrays.asList(preferredWeathers));
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getTopSpeed() {
        return topSpeed;
    }

    public float getTimeRequiredToCrossACrater() {
        return timeRequiredToCrossACrater;
    }

    public Set<Weather> getPreferredWeathers() {
        return preferredWeathers;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", topSpeed=" + topSpeed +
                ", timeRequiredToCrossACrater=" + timeRequiredToCrossACrater +
                ", preferredWeathers=" + preferredWeathers +
                '}';
    }
}
