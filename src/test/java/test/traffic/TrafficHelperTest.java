package test.traffic;

import com.hokageinc.models.Places;
import com.hokageinc.models.Weather;
import com.hokageinc.models.World;
import com.hokageinc.traffic.Lengaburu;
import com.hokageinc.traffic.Route;
import com.hokageinc.traffic.TrafficHelper;
import com.hokageinc.traffic.TravelSuggestion;
import models.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import traffic.*;

@DisplayName("TrafficHelper Tests")
public class TrafficHelperTest {

    @Test
    public void given_lengaburu_sunny_weather_orbitSpeed_12_10_suggest_fastestVehicleAndOrbit(){
        // given
        World world = new Lengaburu();
        world.setWeather(new Weather("Sunny", 10));

        world.setLiveOrbitSpeed(1, 12);
        world.setLiveOrbitSpeed(2, 10);

        // when
        TrafficHelper trafficHelper = new TrafficHelper();

        Route route = new Route(Places.SILK_DORB);
        route.addToVisit(Places.HALLITHARAM);

        TravelSuggestion travelSuggestion = trafficHelper.getFastestTravelSuggestion(world, route);

        // then
        Assertions.assertEquals(world.getOrbit(1), travelSuggestion.getOrbits().get(0));
        Assertions.assertEquals(world.getVehicle(2), travelSuggestion.getVehicle());
    }

    @Test
    public void given_lengaburu_windy_weather_orbitSpeed_14_20_suggest_fastestVehicleAndOrbit(){
        // given
        World world = new Lengaburu();
        world.setWeather(new Weather("Windy", 0));

        world.setLiveOrbitSpeed(1, 14);
        world.setLiveOrbitSpeed(2, 20);

        // when
        TrafficHelper trafficHelper = new TrafficHelper();

        Route route = new Route(Places.SILK_DORB);
        route.addToVisit(Places.HALLITHARAM);

        TravelSuggestion travelSuggestion = trafficHelper.getFastestTravelSuggestion(world, route);

        // then
        Assertions.assertEquals(world.getOrbit(2), travelSuggestion.getOrbits().get(0));
        Assertions.assertEquals(world.getVehicle(3), travelSuggestion.getVehicle());
    }

    @Test
    public void given_two_destinations_shouldFindOptimalPath(){
        // given
        World world = new Lengaburu();
        world.setWeather(new Weather("Windy", 0));

        world.setLiveOrbitSpeed(1, 20);
        world.setLiveOrbitSpeed(2, 12);
        world.setLiveOrbitSpeed(3, 15);
        world.setLiveOrbitSpeed(4, 12);

        // when
        TrafficHelper trafficHelper = new TrafficHelper();

        Route route = new Route(Places.SILK_DORB);
        route.addToVisit(Places.HALLITHARAM);
        route.addToVisit(Places.R_K_PURAM);

        TravelSuggestion travelSuggestion = trafficHelper.getFastestTravelSuggestion(world, route);

        // then
        Assertions.assertEquals(2, travelSuggestion.getOrbits().size());
        Assertions.assertEquals(world.getOrbit(3), travelSuggestion.getOrbits().get(0));
        Assertions.assertEquals(world.getOrbit(4), travelSuggestion.getOrbits().get(1));
        Assertions.assertEquals(world.getVehicle(3), travelSuggestion.getVehicle());
    }
}
