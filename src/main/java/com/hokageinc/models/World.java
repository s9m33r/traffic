package com.hokageinc.models;

import java.util.*;

public class World {
    private Weather weather;
    private Map<Integer, Place> places = new HashMap<>();
    private Map<Integer, Orbit> orbits = new HashMap<>();
    private Map<Integer, Vehicle> vehicles = new HashMap<>();
    private Orbit[][] map;

    public World(int maximumNumberOfPlaces) {
        map = new Orbit[maximumNumberOfPlaces][maximumNumberOfPlaces];
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
        orbits.values().forEach(orbit -> orbit.updateNumberOfCratersForWeather(weather));
    }

    public Place getPlace(String nameOfThePlace) {
        return Optional.of(places.values().stream().
                filter(place -> place.getName().equalsIgnoreCase(nameOfThePlace)).
                findFirst()).
                get().
                orElseThrow(PlaceByTheNameDoseNotExistsInTheWorld::new);
    }

    public List<Place> getPlaces() {
        return new ArrayList<>(places.values());
    }

    public void addPlace(String nameOfThePlace) {
        places.put(places.size(), new Place(places.size(), nameOfThePlace));
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

        Orbit orbit = new Orbit(orbits.size(), nameOfTheOrbit, length, numberOfCraters,
                startingPlace, endingPlace);
        orbits.put(orbits.size(), orbit);

        map[startingPlace.getId()][endingPlace.getId()] = orbit;
        map[endingPlace.getId()][startingPlace.getId()] = orbit;
    }

    public Vehicle getVehicle(int vehicleId) {
        return vehicles.get(vehicleId);
    }

    public List<Vehicle> getVehicles() {
        return new ArrayList<>(vehicles.values());
    }

    protected void addVehicle(String name, float topSpeed, float timeRequiredToCrossACrater) {
        vehicles.put(vehicles.size(),
                new Vehicle(vehicles.size(), name, topSpeed, timeRequiredToCrossACrater));
    }

    public Orbit[][] getMap() {
        return map;
    }

    public void setLiveOrbitSpeed(int orbitId, int liveSpeed) {
        orbits.get(orbitId).setTrafficSpeed(liveSpeed);
    }

    public class PlaceByTheNameDoseNotExistsInTheWorld extends RuntimeException {
    }
}
