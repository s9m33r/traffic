package com.hokageinc.traffic;

import com.hokageinc.models.Orbit;
import com.hokageinc.models.Place;
import com.hokageinc.models.Vehicle;
import com.hokageinc.models.World;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class TrafficHelper {
    public TravelSuggestion getFastestTravelSuggestion(World world, Route route) throws NoOptimalRouteFound {
        TravelSuggestion finalTravelSuggestion = new TravelSuggestion();
        finalTravelSuggestion.addToTotalTimeTaken(Float.MAX_VALUE);

        List<Vehicle> vehicles = world.getVehicles();
        Orbit[][] map = world.getMap();

        for (Vehicle vehicle : vehicles) {
            Map<Integer, Place> placesToVisit = route.getCheckpoints().stream().
                    collect(Collectors.toMap(x -> x.getId(), x -> x));

            Place currentPlace = route.getBeginning();

            TravelSuggestion travelSuggestion = new TravelSuggestion();
            travelSuggestion.setVehicle(vehicle);

            while (!placesToVisit.isEmpty()) {
                MinimumTimeTracker minimumTimeTracker = new MinimumTimeTracker(Float.MAX_VALUE);
                Arrays.stream(map[currentPlace.getId()]).filter(orbit -> Objects.nonNull(orbit) &&
                        placesToVisit.containsKey(orbit.getEnd().getId())).forEach(
                        orbit ->
                                minimumTimeTracker.setIfMinimum(orbit.timeTakenWith(vehicle), vehicle, orbit));

                if (Objects.isNull(minimumTimeTracker.optimalOrbit) || Objects.isNull(minimumTimeTracker.optimalVehicle))
                    throw new NoOptimalRouteFound();
                else {
                    travelSuggestion.addOrbit(minimumTimeTracker.optimalOrbit);
                    travelSuggestion.addToTotalTimeTaken(minimumTimeTracker.currentMinimum);
                    currentPlace = minimumTimeTracker.optimalOrbit.getEnd();
                    placesToVisit.remove(currentPlace.getId());
                }
            }

            if (travelSuggestion.getTotalTimeTaken() < finalTravelSuggestion.getTotalTimeTaken()) {
                finalTravelSuggestion.setTotalTimeTaken(travelSuggestion.getTotalTimeTaken());
                finalTravelSuggestion.setVehicle(travelSuggestion.getVehicle());
                finalTravelSuggestion.setOrbits(travelSuggestion.getOrbits());
            }
        }

        return finalTravelSuggestion;
    }

    private class MinimumTimeTracker {
        float currentMinimum;
        Vehicle optimalVehicle;
        Orbit optimalOrbit;

        MinimumTimeTracker(float currentMinimum) {
            this.currentMinimum = currentMinimum;
        }

        private void setIfMinimum(float testValue, Vehicle vehicle, Orbit orbit) {
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
