package com.hokageinc.traffic;

import com.hokageinc.models.Orbit;
import com.hokageinc.models.Places;
import com.hokageinc.models.Vehicle;
import com.hokageinc.models.World;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TrafficHelper {
    public TravelSuggestion getFastestTravelSuggestion(World world, Route route) throws NoOptimalRouteFound {
        float minTimeTaken = Float.MAX_VALUE;
        Orbit optimalOrbit = null;
        Vehicle optimalVehicle = null;

        List<Orbit> orbitsToBeginWith = world.getOrbits().stream().
                filter(orbit ->
                        orbit.getConnectedPlaces()[0].equals(route.getBeginning()) ||
                        orbit.getConnectedPlaces()[1].equals(route.getBeginning())).
                collect(Collectors.toList());
        Map<Places, Boolean> placesToVisit = route.getPlacesToVisit().stream().
                collect(Collectors.toMap(x-> x , x-> false));

        MinimumTimeTracker minimumTimeTracker = new MinimumTimeTracker(Float.MAX_VALUE);

        List<Vehicle> vehicles = world.getVehicles();

        float timeTaken;
        for(Vehicle vehicle: vehicles){
            for(Orbit orbit: orbitsToBeginWith){
                timeTaken = orbit.timeTakenWith(vehicle);
                if(timeTaken < minTimeTaken ||
                  (timeTaken == minTimeTaken && vehicle.getName().equals("Bike"))) {
                    minTimeTaken = timeTaken;
                    optimalOrbit = orbit;
                    optimalVehicle = vehicle;
                }
            }
        }

        if(Objects.isNull(optimalOrbit) && Objects.isNull(optimalVehicle))
            throw new NoOptimalRouteFound();

        TravelSuggestion travelSuggestion = new TravelSuggestion(optimalVehicle);
        travelSuggestion.addOrbit(optimalOrbit);

        return travelSuggestion;
    }

    private class MinimumTimeTracker {
        float currentMinimum;
        Vehicle optimalVehicle;
        Orbit optimalOrbit;

        Predicate<Float> strictlyLessThanCurrentMinimum = testValue -> testValue < currentMinimum;
        

        MinimumTimeTracker(float currentMinimum){
            this.currentMinimum = currentMinimum;
        }

        void setIfMinimum(Vehicle vehicle, Orbit orbit, float testValue){
            currentMinimum = testValue < currentMinimum ? testValue : currentMinimum;
            currentMinimum = testValue == currentMinimum && "Bike".equals(vehicle.getName())
                    ? testValue : currentMinimum;
        }
    }

    public class NoOptimalRouteFound extends RuntimeException{ }
}
