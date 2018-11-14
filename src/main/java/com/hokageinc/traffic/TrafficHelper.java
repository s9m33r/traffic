package com.hokageinc.traffic;

import com.hokageinc.models.Orbit;
import com.hokageinc.models.Vehicle;
import com.hokageinc.models.World;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class TrafficHelper {
    public TravelSuggestion getFastestTravelSuggestion(World world, Route route) throws NoOptimalRouteFound {
        List<Orbit> orbitsToBeginWith = world.getOrbits().stream().
                filter(orbit ->
                        orbit.getStart().getName().equals(route.getBeginning()) ||
                                orbit.getEnd().getName().equals(route.getBeginning())).
                collect(Collectors.toList());

        Map<String, Boolean> placesToVisit = route.getPlacesToVisit().stream().
                collect(Collectors.toMap(x -> x, x -> false));

        List<Vehicle> vehicles = world.getVehicles();

        MinimumTimeTracker minimumTimeTracker = new MinimumTimeTracker(Float.MAX_VALUE);

        float timeTaken;
        for (Vehicle vehicle : vehicles) {
            for (Orbit orbit : orbitsToBeginWith) {
                if (placesToVisit.containsKey(orbit.getEnd().getName())) {
                    timeTaken = orbit.timeTakenWith(vehicle);
                    minimumTimeTracker.setIfMinimum(timeTaken, vehicle, orbit);
                }
            }
        }

        if (Objects.isNull(minimumTimeTracker.optimalOrbit) ||
                Objects.isNull(minimumTimeTracker.optimalVehicle))
            throw new NoOptimalRouteFound();

        TravelSuggestion travelSuggestion = new TravelSuggestion(minimumTimeTracker.optimalVehicle);
        travelSuggestion.addOrbit(minimumTimeTracker.optimalOrbit);

        return travelSuggestion;
    }

    private class MinimumTimeTracker {
        float currentMinimum;
        Vehicle optimalVehicle;
        Orbit optimalOrbit;

        MinimumTimeTracker(float currentMinimum) {
            this.currentMinimum = currentMinimum;
        }

        void setIfMinimum(float testValue, Vehicle vehicle, Orbit orbit) {
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

    public class NoOptimalRouteFound extends RuntimeException {
    }
}
