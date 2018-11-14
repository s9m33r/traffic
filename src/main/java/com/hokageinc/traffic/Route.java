package com.hokageinc.traffic;

import java.util.ArrayList;
import java.util.List;

public class Route {
    private String beginning;
    private List<String> checkpoints = new ArrayList<>();

    public Route(String beginning) {
        this.beginning = beginning;
    }

    public String getBeginning() {
        return beginning;
    }

    public void setBeginning(String beginning) {
        this.beginning = beginning;
    }

    public void addToVisit(String place) {
        checkpoints.add(place);
    }

    public List<String> getPlacesToVisit() {
        return checkpoints;
    }
}
