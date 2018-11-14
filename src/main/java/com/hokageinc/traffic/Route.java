package com.hokageinc.traffic;

import com.hokageinc.models.Places;

import java.util.ArrayList;
import java.util.List;

public class Route {
    private Places beginning;
    private List<Places> toVisit = new ArrayList<>();

    public Route(Places beginning){
        this.beginning = beginning;
    }

    public void setBeginning(Places beginning) {
        this.beginning = beginning;
    }

    public Places getBeginning() {
        return beginning;
    }

    public void addToVisit(Places place){
        toVisit.add(place);
    }

    public List<Places> getPlacesToVisit(){
        return toVisit;
    }
}
