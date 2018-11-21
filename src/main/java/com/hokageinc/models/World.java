package com.hokageinc.models;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Hosts the state on an imaginary world.
 * Like, weathers it has,
 * vehicles it has,
 * places it has,
 * orbits it has.
 * What's the current weather?
 * What connects what?
 */
public class World {
    private static final String placesPairFormat = "%s-%s";

    private Weather currentWeather;
    private Map<Integer, Weather> weathers = new HashMap<>();
    private Map<Integer, Place> places = new HashMap<>();
    private Map<Integer, Orbit> orbits = new HashMap<>();
    private Map<Integer, Vehicle> vehicles = new HashMap<>();
    private Map<Integer, Set<Place>> connectedPlacesMap = new HashMap<>();
    private Map<Integer, List<Place>> orbitsToPlacesMap = new HashMap<>();
    private Map<String, Set<Orbit>> placesPairToOrbitsMap = new HashMap<>();

    public Weather getCurrentWeather() {
        return currentWeather;
    }

    public void setCurrentWeather(Weather currentWeather) {
        this.currentWeather = currentWeather;
        orbits.values().forEach(orbit -> orbit.updateNumberOfCratersForWeather(currentWeather));
    }

    public Weather getWeather(String nameOfTheWeather) {
        return weathers.values().stream().
                filter(weather -> weather.getName().equalsIgnoreCase(nameOfTheWeather)).
                findFirst().
                orElseThrow(NoSuchWeatherExistsInTheWorld::new);
    }

    public Set<Weather> getWeathers() {
        return new HashSet<>(weathers.values());
    }

    public Weather addWeather(String name, float craterReductionFactor) {
        Weather weather = new Weather(weathers.size(), name, craterReductionFactor);
        weathers.put(weathers.size(), weather);
        return weather;
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

    public Place addPlace(String name) {
        Place place = new Place(places.size(), name);
        places.put(places.size(), place);
        return place;
    }

    public Orbit getOrbit(int orbitId) {
        return orbits.get(orbitId);
    }

    public List<Orbit> getOrbits() {
        return new ArrayList<>(orbits.values());
    }

    public Orbit addOrbit(String name, float length, float numberOfCraters,
                          String placeNameA, String placeNameB) {
        Place placeA = Optional.of(places.values().stream().
                filter(place -> place.getName().equalsIgnoreCase(placeNameA)).findAny()).
                get().
                orElseThrow(PlaceByTheNameDoseNotExistsInTheWorld::new);

        Place placeB = Optional.of(places.values().stream().
                filter(place -> place.getName().equalsIgnoreCase(placeNameB)).findAny()).
                get().
                orElseThrow(PlaceByTheNameDoseNotExistsInTheWorld::new);

        Orbit orbit = new Orbit(orbits.size(), name, length, numberOfCraters,
                placeA, placeB);
        orbits.put(orbits.size(), orbit);

        connect(placeA, placeB, orbit);

        return orbit;
    }

    private void connect(Place placeA, Place placeB, Orbit orbit) {
        connectedPlacesMap.computeIfAbsent(placeA.getId(), place -> new HashSet<>()).add(placeB);
        connectedPlacesMap.computeIfAbsent(placeB.getId(), place -> new HashSet<>()).add(placeA);

        String placesPair = String.format(placesPairFormat, placeA.getId(), placeB.getId());
        placesPairToOrbitsMap.computeIfAbsent(placesPair, key -> new HashSet<>()).add(orbit);

        String placesPairReversed = String.format(placesPairFormat, placeB.getId(), placeA.getId());
        placesPairToOrbitsMap.computeIfAbsent(placesPairReversed, key -> new HashSet<>()).add(orbit);

        orbitsToPlacesMap.put(orbit.getId(), Arrays.asList(placeA, placeB));
    }

    public Vehicle getVehicle(int vehicleId) {
        return vehicles.get(vehicleId);
    }

    public List<Vehicle> getVehicles() {
        return new ArrayList<>(vehicles.values());
    }

    public Vehicle addVehicle(String name, float topSpeed, float timeRequiredToCrossACrater, Weather... preferredWeathers) {
        Vehicle vehicle = new Vehicle(vehicles.size(), name, topSpeed, timeRequiredToCrossACrater, preferredWeathers);
        vehicles.put(vehicles.size(), vehicle);
        return vehicle;
    }

    public void setLiveOrbitSpeed(int orbitId, int liveSpeed) {
        orbits.get(orbitId).setTrafficSpeed(liveSpeed);
    }

    public Set<Place> getPlacesReachableFrom(int placeId) {
        return connectedPlacesMap.get(placeId);
    }

    public Set<Place> getPlacesReachableFrom(Place place) {
        return getPlacesReachableFrom(place.getId());
    }

    public Set<Orbit> getOrbitsConnecting(Place firstPlace, Place secondPlace) {
        String placesPair = String.format(placesPairFormat, firstPlace.getId(), secondPlace.getId());

        return placesPairToOrbitsMap.get(placesPair);
    }

    public List<Place> getPlacesConnectedBy(int orbitId) {
        return orbitsToPlacesMap.get(orbitId);
    }

    public List<Place> getPlacesConnectedBy(Orbit orbit) {
        return getPlacesConnectedBy(orbit.getId());
    }

    public Set<Vehicle> getPreferredVehicles() {
        return vehicles.values().stream().
                filter(vehicle -> vehicle.getPreferredWeathers().contains(currentWeather)).
                collect(Collectors.toSet());
    }

    public class PlaceByTheNameDoseNotExistsInTheWorld extends RuntimeException {
    }

    public class NoSuchWeatherExistsInTheWorld extends RuntimeException {
    }
}
