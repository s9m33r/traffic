package com.hokageinc.traffic;

import com.hokageinc.models.Orbit;
import com.hokageinc.models.Place;
import com.hokageinc.models.Vehicle;
import com.hokageinc.models.World;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Hosts the logic to provide the best possible combination of vehicle and orbit to travel.
 */
public class TrafficHelper {
    /**
     * Provides travelSuggestion which gives information about the vehicle and the order in which
     * the orbits must be taken for optimal travel to given places.
     *
     * @param world Defines the laws and state of an imaginary world.
     * @param route Contains places which needs to be visited.
     * @return TravelSuggestion about the vehicle to be used and orbits to be taken.
     * @throws NoOptimalRouteFound in case, there are no feasible way to reach all the places
     *                             provided in the route.
     */
    public TravelSuggestion getFastestTravelSuggestion(World world, Route route) throws NoOptimalRouteFound {
        Set<Orbit> infeasibleOrbits = new HashSet<>();

        TravelSuggestion finalTravelSuggestion = new TravelSuggestion();

        Set<Vehicle> vehicles = world.getPreferredVehicles();

        for (Vehicle vehicle : vehicles) {
            TravelSuggestion travelSuggestion = getTravelSuggestion(route, vehicle, world, infeasibleOrbits);

            if (finalTravelSuggestion.getTotalTimeTaken() == 0 ||
                    travelSuggestion.getTotalTimeTaken() < finalTravelSuggestion.getTotalTimeTaken()
            ) {
                finalTravelSuggestion.setTotalTimeTaken(travelSuggestion.getTotalTimeTaken());
                finalTravelSuggestion.setVehicle(travelSuggestion.getVehicle());
                finalTravelSuggestion.setOrbits(travelSuggestion.getOrbits());
            }
        }

        if (Objects.isNull(finalTravelSuggestion.getVehicle()) || Objects.isNull(finalTravelSuggestion.getOrbits())
                || finalTravelSuggestion.getOrbits().size() == 0)
            throw new NoOptimalRouteFound();

        return finalTravelSuggestion;
    }

    private TravelSuggestion getTravelSuggestion(Route route, Vehicle vehicle, World world, Set<Orbit> infeasibleOrbits) {
        TravelSuggestion travelSuggestion = new TravelSuggestion();
        travelSuggestion.setVehicle(vehicle);

        Map<Integer, Place> placesToVisit = route.getCheckpoints().stream().
                collect(Collectors.toMap(x -> x.getId(), x -> x));

        Place currentPlace = route.getBeginning();

        Orbit lastAddedOrbit = null;
        Place lastVisitedPlace = null;

        while (!placesToVisit.isEmpty()) {
            MinimumTimeTracker minimumTimeTracker = MinimumTimeTracker.create();

            List<Place> reachableAndToBeVisitedPlaces = world.getPlacesReachableFrom(currentPlace).stream().
                    filter(place ->
                            placesToVisit.containsKey(place.getId())).
                    collect(Collectors.toList());

            if (reachableAndToBeVisitedPlaces.isEmpty()) {
                infeasibleOrbits.add(lastAddedOrbit);
                travelSuggestion.rollBackLastSuggestion();
                placesToVisit.put(lastVisitedPlace.getId(), lastVisitedPlace);
                currentPlace = lastVisitedPlace;
                continue;
            }

            List<Orbit> orbitsToEvaluate = new ArrayList<>();

            for (Place place : reachableAndToBeVisitedPlaces) {
                orbitsToEvaluate.addAll(world.getOrbitsConnecting(currentPlace, place));
            }

            orbitsToEvaluate.forEach(orbit ->
                    minimumTimeTracker.setIfMinimum(orbit.timeTakenWith(vehicle), vehicle, orbit));

            lastAddedOrbit = minimumTimeTracker.getOptimalOrbit();

            List<Place> currentPlaceAndLastAddedPlace = world.getPlacesConnectedBy(lastAddedOrbit);
            lastVisitedPlace = currentPlaceAndLastAddedPlace.get(0).getId() == currentPlace.getId() ?
                    currentPlaceAndLastAddedPlace.get(1) : currentPlaceAndLastAddedPlace.get(0);

            travelSuggestion.add(
                    minimumTimeTracker.getCurrentMinimum(),
                    minimumTimeTracker.getOptimalOrbit());

            placesToVisit.remove(lastVisitedPlace.getId());

            currentPlace = lastVisitedPlace;
        }
        return travelSuggestion;
    }

    public class NoOptimalRouteFound extends RuntimeException {
    }
}
