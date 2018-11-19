package com.hokageinc.traffic;

import com.hokageinc.models.Orbit;
import com.hokageinc.models.Vehicle;

public class MinimumTimeTracker {
    private float currentMinimum;
    private Vehicle optimalVehicle;
    private Orbit optimalOrbit;

    private MinimumTimeTracker(float currentMinimum) {
        this.currentMinimum = currentMinimum;
    }

    public static MinimumTimeTracker create() {
        return new MinimumTimeTracker(Float.MAX_VALUE);
    }

    public float getCurrentMinimum() {
        return currentMinimum;
    }

    public Vehicle getOptimalVehicle() {
        return optimalVehicle;
    }

    public Orbit getOptimalOrbit() {
        return optimalOrbit;
    }

    public void setIfMinimum(float testValue, Vehicle vehicle, Orbit orbit) {
        if (isMinimum(testValue, vehicle)) {
            currentMinimum = testValue;
            optimalOrbit = orbit;
            optimalVehicle = vehicle;
        }
    }

    private boolean isMinimum(float testValue, Vehicle vehicle) {
        return testValue < currentMinimum ||
                (testValue == currentMinimum && "Bike".equals(vehicle.getName()));
    }
}
