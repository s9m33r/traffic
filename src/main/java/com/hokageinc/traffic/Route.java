package com.hokageinc.traffic;

import com.hokageinc.models.Place;

import java.util.ArrayList;
import java.util.List;

public class Route {
    private Place beginning;
    private List<Place> checkpoints = new ArrayList<>();
    private boolean shouldTravelCheckpointsInOrder = true;

    public Route(Place beginning) {
        this.beginning = beginning;
    }

    public Route(Place beginning, boolean shouldTravelCheckpointsInOrder) {
        this.beginning = beginning;
        this.shouldTravelCheckpointsInOrder = shouldTravelCheckpointsInOrder;
    }

    public Place getBeginning() {
        return beginning;
    }

    public void setBeginning(Place beginning) {
        this.beginning = beginning;
    }

    public void addCheckpoint(Place place) {
        checkpoints.add(place);
    }

    public List<Place> getCheckpoints() {
        return checkpoints;
    }

    public boolean isShouldTravelCheckpointsInOrder() {
        return shouldTravelCheckpointsInOrder;
    }

    public void setShouldTravelCheckpointsInOrder(boolean shouldTravelCheckpointsInOrder) {
        this.shouldTravelCheckpointsInOrder = shouldTravelCheckpointsInOrder;
    }
}
