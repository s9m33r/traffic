package com.hokageinc.traffic;

import com.hokageinc.models.Orbit;
import com.hokageinc.models.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class TravelSuggestion {
    private Vehicle vehicle;
    private List<Orbit> orbits = new ArrayList<>();

    public TravelSuggestion(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public List<Orbit> getOrbits() {
        return orbits;
    }

    public void addOrbit(Orbit orbit){
        orbits.add(orbit);
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}
