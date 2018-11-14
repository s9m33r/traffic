package test.traffic;

import com.hokageinc.models.Weather;
import com.hokageinc.models.World;
import com.hokageinc.traffic.Lengaburu;
import com.hokageinc.traffic.Route;
import com.hokageinc.traffic.TrafficHelper;
import com.hokageinc.traffic.TravelSuggestion;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TrafficHelperTest {

    @Test
    public void given_lengaburu_sunny_weather_orbitSpeed_12_10_suggest_fastestVehicleAndOrbit(){
        // given
        World world = new Lengaburu();

        world.setWeather(new Weather("Sunny", 10));

        world.setLiveOrbitSpeed(0, 12);
        world.setLiveOrbitSpeed(1, 10);

        // when
        TrafficHelper trafficHelper = new TrafficHelper();

        Route route = new Route("SILK_DORB");
        route.addToVisit("HALLITHARAM");

        TravelSuggestion travelSuggestion = trafficHelper.getFastestTravelSuggestion(world, route);

        // then
        assertEquals(world.getOrbit(0), travelSuggestion.getOrbits().get(0));
        assertEquals(world.getVehicle(1), travelSuggestion.getVehicle());
    }

    @Test
    public void given_lengaburu_windy_weather_orbitSpeed_14_20_suggest_fastestVehicleAndOrbit(){
        // given
        World world = new Lengaburu();

        world.setWeather(new Weather("Windy", 0));

        world.setLiveOrbitSpeed(0, 14);
        world.setLiveOrbitSpeed(1, 20);

        // when
        TrafficHelper trafficHelper = new TrafficHelper();

        Route route = new Route("SILK_DORB");
        route.addToVisit("HALLITHARAM");

        TravelSuggestion travelSuggestion = trafficHelper.getFastestTravelSuggestion(world, route);

        // then
        assertEquals(world.getOrbit(1), travelSuggestion.getOrbits().get(0));
        assertEquals(world.getVehicle(2), travelSuggestion.getVehicle());
    }

    @Test
    public void given_two_destinations_shouldFindOptimalPath(){
        // given
        World world = new Lengaburu();

        world.setWeather(new Weather("Windy", 0));

        world.setLiveOrbitSpeed(0, 20);
        world.setLiveOrbitSpeed(1, 12);
        world.setLiveOrbitSpeed(2, 15);
        world.setLiveOrbitSpeed(3, 12);

        // when
        TrafficHelper trafficHelper = new TrafficHelper();

        Route route = new Route("SILK_DORB");
        route.addToVisit("HALLITHARAM");
        route.addToVisit("RK_PURAM");

        TravelSuggestion travelSuggestion = trafficHelper.getFastestTravelSuggestion(world, route);

        // then
        assertEquals(2, travelSuggestion.getOrbits().size());
        assertEquals(world.getOrbit(2), travelSuggestion.getOrbits().get(0));
        assertEquals(world.getOrbit(3), travelSuggestion.getOrbits().get(1));
        assertEquals(world.getVehicle(2), travelSuggestion.getVehicle());
    }
}
