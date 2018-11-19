package test.model;

import com.hokageinc.models.*;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WorldTest {
    private static World world;

    private static Weather testWeather1;
    private static Weather testWeather2;
    private static Weather testWeather3;

    private static Vehicle testVehicle1;
    private static Vehicle testVehicle2;

    private static Place firstPlace;
    private static Place secondPlace;
    private static Place thirdPlace;

    private static Orbit testOrbit1;
    private static Orbit testOrbit2;
    private static Orbit testOrbit3;

    @BeforeClass
    public static void globalSetup() {
        // given
        world = new World();

        testWeather1 = world.addWeather("Test Weather 1", 10);
        testWeather2 = world.addWeather("Test Weather 2", 0);
        testWeather3 = world.addWeather("Test Weather 3", -20);

        testVehicle1 = world.addVehicle("Test Vehicle 1", 10, 0.2f,
                testWeather1, testWeather2, testWeather3);

        testVehicle2 = world.addVehicle("Test Vehicle 2", 10, 0.2f,
                testWeather1, testWeather2);

        firstPlace = world.addPlace("First Place");
        secondPlace = world.addPlace("Second Place");
        thirdPlace = world.addPlace("Third Place");

        testOrbit1 = world.addOrbit("Test Orbit 1", 19, 2,
                "First Place", "Second Place");
        testOrbit2 = world.addOrbit("Test Orbit 2", 112, 12,
                "First Place", "Third Place");
        testOrbit3 = world.addOrbit("Test Orbit 3", 50, 12,
                "First Place", "Second Place");

    }

    @Test(expected = World.PlaceByTheNameDoseNotExistsInTheWorld.class)
    public void inTheWorldTheOrbitsCannotBeAddedForPlaceWhichDoesNotExists() {
        // when
        world.addOrbit("Test Orbit", 10, 2,
                "First Place", "Unknown Place");
    }

    @Test
    public void shouldGiveImmediateConnectedPlaces() {
        // when
        Set<Place> placesConnectedToFirstPlace = world.getPlacesReachableFrom(firstPlace);
        Set<Place> placesConnectedToSecondPlace = world.getPlacesReachableFrom(secondPlace);
        Set<Place> placesConnectedToThirdPlace = world.getPlacesReachableFrom(thirdPlace);

        // then
        assertEquals(2, placesConnectedToFirstPlace.size());
        assertTrue(placesConnectedToFirstPlace.contains(world.getPlace("Second Place")));
        assertTrue(placesConnectedToFirstPlace.contains(world.getPlace("Third Place")));

        assertEquals(1, placesConnectedToSecondPlace.size());
        assertTrue(placesConnectedToSecondPlace.contains(world.getPlace("First Place")));

        assertEquals(1, placesConnectedToSecondPlace.size());
        assertTrue(placesConnectedToThirdPlace.contains(world.getPlace("First Place")));
    }

    @Test
    public void shouldProvideWhichOrbitConnectsTwoPlaces() {
        // when
        Set<Orbit> orbitsConnectingFirstSecond = world.getOrbitsConnecting(firstPlace, secondPlace);
        Set<Orbit> orbitsConnectingFirstThird = world.getOrbitsConnecting(firstPlace, thirdPlace);

        // then
        assertTrue(orbitsConnectingFirstSecond.contains(testOrbit1));
        assertTrue(orbitsConnectingFirstThird.contains(testOrbit2));
    }

    @Test
    public void canHaveMultipleOrbitsBetweenTwoPlaces() {
        // when
        Set<Orbit> orbitsConnectingFirstSecond = world.getOrbitsConnecting(firstPlace, secondPlace);

        // then
        assertEquals(2, orbitsConnectingFirstSecond.size());
        assertTrue(orbitsConnectingFirstSecond.contains(testOrbit1));
        assertTrue(orbitsConnectingFirstSecond.contains(testOrbit3));
    }

    @Test
    public void shouldProvidePlacesConnectedByAGivenOrbit() {
        //  when
        List<Place> connectedPlaces = world.getPlacesConnectedBy(testOrbit1);

        // then
        assertTrue(connectedPlaces.contains(firstPlace));
        assertTrue(connectedPlaces.contains(secondPlace));
    }

    @Test
    public void shouldProvideVehiclesPerTheWeather() {
        // given
        world.setCurrentWeather(testWeather2);

        // when
        Set<Vehicle> vehiclesForTestWeather2 = world.getPreferredVehicles();

        // then
        assertEquals(2, vehiclesForTestWeather2.size());
        assertTrue(vehiclesForTestWeather2.contains(testVehicle1));
        assertTrue(vehiclesForTestWeather2.contains(testVehicle2));
    }
}
