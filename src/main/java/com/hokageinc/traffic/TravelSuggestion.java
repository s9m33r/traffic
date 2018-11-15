package com.hokageinc.traffic;

import com.hokageinc.models.Orbit;
import com.hokageinc.models.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class TravelSuggestion {
    private float totalTimeTaken;
    private Vehicle vehicle;
    private List<Orbit> orbits = new ArrayList<>();

    public float getTotalTimeTaken() {
        return totalTimeTaken;
    }

    public void setTotalTimeTaken(float totalTimeTaken) {
        this.totalTimeTaken = totalTimeTaken;
    }

    public void addToTotalTimeTaken(float delta) {
        totalTimeTaken += delta;
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

    public void addOrbit(Orbit orbit){
        orbits.add(orbit);
    }

    public void setOrbits(List<Orbit> orbits) {
        this.orbits = orbits;
    }
}
