package com.hokageinc.traffic;

import com.hokageinc.models.Orbit;
import com.hokageinc.models.Vehicle;
import com.hokageinc.models.World;
import com.hokageinc.models.Places;

public class Lengaburu extends World {

    public Lengaburu(){
        super();

        addOrbit(new Orbit(1, "Orbit 1", 18, 20,
                new Places[]{Places.SILK_DORB, Places.HALLITHARAM}));
        addOrbit(new Orbit(2, "Orbit 2", 20, 10,
                new Places[]{Places.SILK_DORB, Places.HALLITHARAM}));
        addOrbit(new Orbit(3, "Orbit 3", 30, 15,
                new Places[]{Places.SILK_DORB, Places.R_K_PURAM}));
        addOrbit(new Orbit(4, "Orbit 4", 15, 18,
                new Places[]{Places.R_K_PURAM, Places.HALLITHARAM}));

        addVehicle(new Vehicle(1, "Bike", 10, 2));
        addVehicle(new Vehicle(2, "TUK TUK", 12, 1));
        addVehicle(new Vehicle(3, "Car", 20, 3));
    }
}
