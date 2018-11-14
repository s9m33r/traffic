package com.hokageinc.models;

import java.util.*;

public class World {
    private Map<Integer, Place> places = new HashMap<>();
    private Weather weather;
    private Map<Integer, Orbit> orbits = new HashMap<>();
    private Map<Integer, Vehicle> vehiclesAvailable = new HashMap<>();

    public List<Place> getPlaces() {
        return new ArrayList<>(places.values());
    }

    public void addPlace(String nameOfThePlace) {
        places.put(places.size(), new Place(places.size(), nameOfThePlace));
    }

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

    protected void addOrbit(String nameOfTheOrbit, float length, float numberOfCraters,
                            String nameOfTheStartingPlace, String nameOfTheEndingPlace) {
        Place startingPlace = Optional.of(places.values().stream().
                filter(place -> place.getName().equalsIgnoreCase(nameOfTheStartingPlace)).findAny()).
                get().
                orElseThrow(PlaceByTheNameDoseNotExistsInTheWorld::new);

        Place endingPlace = Optional.of(places.values().stream().
                filter(place -> place.getName().equalsIgnoreCase(nameOfTheEndingPlace)).findAny()).
                get().
                orElseThrow(PlaceByTheNameDoseNotExistsInTheWorld::new);

        orbits.put(orbits.size(),
                new Orbit(orbits.size(), nameOfTheOrbit, length, numberOfCraters,
                        startingPlace, endingPlace));
    }

    public Vehicle getVehicle(int vehicleId) {
        return vehiclesAvailable.get(vehicleId);
    }

    public List<Vehicle> getVehicles() {
        return new ArrayList<>(vehiclesAvailable.values());
    }

    protected void addVehicle(String name, float topSpeed, float timeRequiredToCrossACrater) {
        vehiclesAvailable.put(vehiclesAvailable.size(),
                new Vehicle(vehiclesAvailable.size(), name, topSpeed, timeRequiredToCrossACrater));
    }

    public void setLiveOrbitSpeed(int orbitId, int liveSpeed) {
        orbits.get(orbitId).setTrafficSpeed(liveSpeed);
    }

    public class PlaceByTheNameDoseNotExistsInTheWorld extends RuntimeException {
    }
}
