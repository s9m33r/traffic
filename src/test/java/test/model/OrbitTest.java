package test.model;

import com.hokageinc.models.Orbit;
import com.hokageinc.models.Place;
import com.hokageinc.models.Vehicle;
import com.hokageinc.models.Weather;
import org.junit.Test;

import java.text.DecimalFormat;

import static org.junit.Assert.assertEquals;

public class OrbitTest {
    private final DecimalFormat decimalFormat = new DecimalFormat("##.00");

    @Test
    public void given_a_weather_determineTheUpdateInNumberOfCraters(){
        Orbit orbit = new Orbit(1, "Test Orbit", 100, 6,
                new Place(1, "SILK_DORB"), new Place(2, "HALLITHARAM"));

        // given
        Weather weather = new Weather("Test Weather", 50);

        // when
        orbit.updateNumberOfCratersForWeather(weather);

        // then
        assertEquals(3, orbit.getWeatherBasedNumberOfCraters(), 0.1);
    }

    @Test
    public void given_a_vehicle_determineTimeTakenToTravelAcross(){
        // given
        Orbit orbit = new Orbit(1, "Test Orbit", 30, 4,
                new Place(1, "SILK_DORB"), new Place(2, "HALLITHARAM"));
        orbit.setTrafficSpeed(5);
        Vehicle vehicle = new Vehicle(1, "Test Vehicle", 10, 2);

        // when
        float timeTaken = orbit.timeTakenWith(vehicle);

        // then
        assertEquals("6.13", decimalFormat.format(timeTaken));
    }

    @Test
    public void given_a_vehicle_and_rainy_weather_determineTimeTakenToTravelAcross(){
        // given
        Vehicle vehicle = new Vehicle(1, "Test Vehicle", 10, 2);

        Orbit orbit = new Orbit(1, "Test Orbit", 30, 5,
                new Place(1, "SILK_DORB"), new Place(2, "HALLITHARAM"));
        orbit.setTrafficSpeed(5);
        orbit.updateNumberOfCratersForWeather(new Weather("Test Weather", -20));

        // when
        float timeTaken = orbit.timeTakenWith(vehicle);

        // then
        assertEquals("6.20", decimalFormat.format(timeTaken));
    }
}
