package test.traffic;

import com.hokageinc.models.Orbit;
import com.hokageinc.models.Place;
import com.hokageinc.models.Vehicle;
import com.hokageinc.traffic.TravelSuggestion;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TravelSuggestionTest {
    private static Place firstPlace;
    private static Place secondPlace;
    private static Place thirdPlace;

    private static Orbit firstOrbit;
    private static Orbit secondOrbit;
    private static Orbit thirdOrbit;

    private static Vehicle bike;

    private TravelSuggestion travelSuggestion;

    @BeforeClass
    public static void globalSetup() {
        // given
        firstPlace = new Place("First Place");
        secondPlace = new Place("Second Place");
        thirdPlace = new Place("Third Place");

        firstOrbit = new Orbit(0, "Test Orbit 1", 19, 2,
                firstPlace, secondPlace);
        secondOrbit = new Orbit(1, "Test Orbit 2", 20, 8,
                secondPlace, thirdPlace);
        thirdOrbit = new Orbit(2, "Test Orbit 3", 40, 10,
                secondPlace, thirdPlace);

        bike = new Vehicle(0, "Bike", 10, 2);
    }

    @Before
    public void setup() {
        // given
        travelSuggestion = new TravelSuggestion();
        travelSuggestion.setVehicle(bike);
    }

    @Test
    public void shouldAddTravelSuggestion() {
        // when
        travelSuggestion.add(10, firstOrbit);
        travelSuggestion.add(20, secondOrbit);

        // then
        Assert.assertEquals(30, travelSuggestion.getTotalTimeTaken(), 0.01f);
        Assert.assertEquals(firstOrbit, travelSuggestion.getOrbits().get(0));
        Assert.assertEquals(secondOrbit, travelSuggestion.getOrbits().get(1));
    }

    @Test
    public void shouldBeAbleToRollBackSuggestion() {
        // when
        travelSuggestion.add(10, firstOrbit);
        travelSuggestion.add(40, thirdOrbit);

        travelSuggestion.rollBackLastSuggestion();

        travelSuggestion.add(20, secondOrbit);

        // then
        Assert.assertEquals(30, travelSuggestion.getTotalTimeTaken(), 0.01f);
        Assert.assertEquals(firstOrbit, travelSuggestion.getOrbits().get(0));
        Assert.assertEquals(secondOrbit, travelSuggestion.getOrbits().get(1));
    }
}
