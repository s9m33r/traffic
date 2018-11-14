package com.hokageinc.traffic;

import com.hokageinc.models.World;

public class Lengaburu extends World {

    public Lengaburu(){
        super();

        addPlace("SILK_DORB");
        addPlace("HALLITHARAM");
        addPlace("R_K_PURAM");

        addOrbit("Orbit 1", 18, 20,
                "SILK_DORB", "HALLITHARAM");
        addOrbit("Orbit 2", 20, 10,
                "SILK_DORB", "HALLITHARAM");
        addOrbit("Orbit 3", 30, 15,
                "SILK_DORB", "R_K_PURAM");
        addOrbit("Orbit 4", 15, 18,
                "R_K_PURAM", "HALLITHARAM");

        addVehicle("Bike", 10, 2);
        addVehicle("TUK TUK", 12, 1);
        addVehicle("Car", 20, 3);
    }
}
