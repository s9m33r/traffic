package com.hokageinc.traffic;

import com.hokageinc.models.Orbit;
import com.hokageinc.models.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class TravelSuggestion {
    private float totalTimeTaken;
    private Vehicle vehicle;
    private List<Orbit> orbits = new ArrayList<>();
    private float lastTimeTaken;
    private Orbit lastAddedOrbit;

    public float getTotalTimeTaken() {
        return totalTimeTaken;
    }

    public void setTotalTimeTaken(float totalTimeTaken) {
        this.totalTimeTaken = totalTimeTaken;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public List<Orbit> getOrbits() {
        return orbits;
    }

    public void add(float timeTaken, Orbit orbit) {
        totalTimeTaken += timeTaken;
        orbits.add(orbit);
        lastTimeTaken = timeTaken;
        lastAddedOrbit = orbit;
    }

    public void setOrbits(List<Orbit> orbits) {
        this.orbits = orbits;
    }

    public void rollBackLastSuggestion() {
        totalTimeTaken -= lastTimeTaken;
        orbits.remove(lastAddedOrbit);
    }
}