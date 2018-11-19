package test.traffic;

import com.hokageinc.models.Orbit;
import com.hokageinc.models.Place;
import com.hokageinc.models.Vehicle;
import com.hokageinc.traffic.MinimumTimeTracker;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class MinimumTimeTrackerTest {
    private static Vehicle bike;
    private static Vehicle vehicle;
    private static Vehicle fasterVehicle;
    private static Orbit orbit;
    private static Orbit shorterOrbit;

    private MinimumTimeTracker minimumTimeTracker;

    @BeforeClass
    public static void globalSetup() {
        bike = new Vehicle(0, "Bike", 20, 2);
        vehicle = new Vehicle(1, "Test Vehicle 1", 10, 2);
        fasterVehicle = new Vehicle(2, "Test Vehicle 1", 20, 2);

        orbit = new Orbit(0, "Test Orbit 1", 40, 3,
                new Place("Test Place 1"),
                new Place("Test Place 2"));
        shorterOrbit = new Orbit(0, "Test Orbit 1", 30, 3,
                new Place("Test Place 1"),
                new Place("Test Place 2"));
    }

    @Before
    public void setup() {
        minimumTimeTracker = MinimumTimeTracker.create();
    }

    @Test
    public void shouldUpdateTheMinimum() {
        minimumTimeTracker.setIfMinimum(10.0f, vehicle, orbit);
        Assert.assertEquals(vehicle, minimumTimeTracker.getOptimalVehicle());
        Assert.assertEquals(orbit, minimumTimeTracker.getOptimalOrbit());

        minimumTimeTracker.setIfMinimum(8.0f, fasterVehicle, shorterOrbit);
        Assert.assertEquals(fasterVehicle, minimumTimeTracker.getOptimalVehicle());
        Assert.assertEquals(shorterOrbit, minimumTimeTracker.getOptimalOrbit());
    }

    @Test
    public void vehicleNamedBikeShouldGetPreferenceAndOverride() {
        minimumTimeTracker.setIfMinimum(10.0f, vehicle, orbit);
        Assert.assertEquals(vehicle, minimumTimeTracker.getOptimalVehicle());
        Assert.assertEquals(orbit, minimumTimeTracker.getOptimalOrbit());

        minimumTimeTracker.setIfMinimum(10.0f, bike, shorterOrbit);
        Assert.assertEquals(bike, minimumTimeTracker.getOptimalVehicle());
        Assert.assertEquals(shorterOrbit, minimumTimeTracker.getOptimalOrbit());
    }

    @Test
    public void vehicleNamedBikeShouldGetPreferenceAndStay() {
        minimumTimeTracker.setIfMinimum(10.0f, bike, shorterOrbit);
        Assert.assertEquals(bike, minimumTimeTracker.getOptimalVehicle());
        Assert.assertEquals(shorterOrbit, minimumTimeTracker.getOptimalOrbit());

        minimumTimeTracker.setIfMinimum(10.0f, vehicle, orbit);
        Assert.assertEquals(bike, minimumTimeTracker.getOptimalVehicle());
        Assert.assertEquals(shorterOrbit, minimumTimeTracker.getOptimalOrbit());
    }
}
