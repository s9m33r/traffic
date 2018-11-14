package com.hokageinc.models;

public class Vehicle {
    private int id;
    private String name;
    private float topSpeed;
    private float timeRequiredToCrossACrater;

    public Vehicle(int id, String name, float topSpeed, float timeRequiredToCrossACrater) {
        this.id = id;
        this.name = name;
        this.topSpeed = topSpeed;
        this.timeRequiredToCrossACrater = timeRequiredToCrossACrater / 60;
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

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", topSpeed=" + topSpeed +
                ", timeRequiredToCrossACrater=" + timeRequiredToCrossACrater +
                '}';
    }
}
