package test.model;

import com.hokageinc.models.Orbit;
import com.hokageinc.models.Vehicle;
import com.hokageinc.models.Weather;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.hokageinc.models.Places;

import java.text.DecimalFormat;

public class OrbitTest {
    private final DecimalFormat decimalFormat = new DecimalFormat("##.00");

    @Test
    public void given_a_weather_determineTheUpdateInNumberOfCraters(){
        Orbit orbit = new Orbit(1, "Test Orbit", 100, 6,
                new Places[]{Places.SILK_DORB, Places.HALLITHARAM});

        // given
        Weather weather = new Weather("Test Weather", 50);

        // when
        orbit.updateNumberOfCratersForWeather(weather);

        // then
        Assertions.assertEquals(3, orbit.getWeatherBasedNumberOfCraters());
    }

    @Test
    public void given_a_vehicle_determineTimeTakenToTravelAcross(){
        // given
        Orbit orbit = new Orbit(1, "Test Orbit", 30, 4,
                new Places[]{Places.SILK_DORB, Places.HALLITHARAM});
        orbit.setTrafficSpeed(5);
        Vehicle vehicle = new Vehicle(1, "Test Vehicle", 10, 2);

        // when
        float timeTaken = orbit.timeTakenWith(vehicle);

        // then
        Assertions.assertEquals("6.13", decimalFormat.format(timeTaken));
    }

    @Test
    public void given_a_vehicle_and_rainy_weather_determineTimeTakenToTravelAcross(){
        // given
        Vehicle vehicle = new Vehicle(1, "Test Vehicle", 10, 2);

        Orbit orbit = new Orbit(1, "Test Orbit", 30, 5,
                new Places[]{Places.SILK_DORB, Places.HALLITHARAM});
        orbit.setTrafficSpeed(5);
        orbit.updateNumberOfCratersForWeather(new Weather("Test Weather", -20));

        // when
        float timeTaken = orbit.timeTakenWith(vehicle);

        // then
        Assertions.assertEquals("6.20", decimalFormat.format(timeTaken));
    }
}
