package com.hokageinc.models;

public class Orbit {
    private int id;
    private String name;
    private float length;
    private float trafficSpeed;
    private float numberOfCraters;
    private float weatherBasedNumberOfCraters;

    public Orbit(int id, String name, float length, float numberOfCraters, Place start, Place end) {
        this.id = id;
        this.name = name;
        this.length = length;
        this.numberOfCraters = numberOfCraters;
        this.weatherBasedNumberOfCraters = numberOfCraters;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getLength() {
        return length;
    }

    public float getNumberOfCraters() {
        return numberOfCraters;
    }

    public float getTrafficSpeed() {
        return trafficSpeed;
    }

    public void setTrafficSpeed(float trafficSpeed) {
        this.trafficSpeed = trafficSpeed;
    }

    public float getWeatherBasedNumberOfCraters() {
        return weatherBasedNumberOfCraters;
    }

    public void updateNumberOfCratersForWeather(Weather weather) {
        weatherBasedNumberOfCraters = numberOfCraters -
                                (numberOfCraters * weather.getCraterReductionFactor() / 100.0f);
    }

    public float timeTakenWith(Vehicle vehicle) {
        float travelSpeed = Math.min(vehicle.getTopSpeed(), trafficSpeed);
        float travelTime = length / travelSpeed;

        float timeTakenToCrossCraters = weatherBasedNumberOfCraters *
                                      vehicle.getTimeRequiredToCrossACrater();

        return travelTime + timeTakenToCrossCraters;
    }

    @Override
    public String toString() {
        return "Orbit{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", length=" + length +
                ", trafficSpeed=" + trafficSpeed +
                ", numberOfCraters=" + numberOfCraters +
                ", weatherBasedNumberOfCraters=" + weatherBasedNumberOfCraters +
                '}';
    }
}
