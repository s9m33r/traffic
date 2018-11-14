package com.hokageinc.models;

import java.util.*;

public class World {
    private Weather weather;
    private Map<Integer, Orbit> orbits = new HashMap<>();
    private Map<Integer, Vehicle> vehiclesAvailable = new HashMap<>();

    public void setWeather(Weather weather) {
        this.weather = weather;
        orbits.values().forEach(orbit -> orbit.updateNumberOfCratersForWeather(weather));
    }

    public Weather getWeather() {
        return weather;
    }

    public Orbit getOrbit(int orbitId) {
        return orbits.get(orbitId);
    }

    public List<Orbit> getOrbits() {
        return new ArrayList<>(orbits.values());
    }

    protected void addOrbit(Orbit orbit) {
        orbits.put(orbit.getId(), orbit);
    }

    public Vehicle getVehicle(int vehicleId) {
        return vehiclesAvailable.get(vehicleId);
    }

    public List<Vehicle> getVehicles() {
        return new ArrayList<>(vehiclesAvailable.values());
    }

    protected void addVehicle(Vehicle vehicle) {
        vehiclesAvailable.put(vehicle.getId(), vehicle);
    }

    public void setLiveOrbitSpeed(int orbitId, int liveSpeed) {
        orbits.get(orbitId).setTrafficSpeed(liveSpeed);
    }
}
