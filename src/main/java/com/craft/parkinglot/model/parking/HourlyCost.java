package com.craft.parkinglot.model.parking;

import com.craft.parkinglot.enums.ParkingSpotType;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class HourlyCost {
    private Map<ParkingSpotType, Double> hourlyCosts = new HashMap<>();

    public HourlyCost() {
        hourlyCosts.put(ParkingSpotType.MOTORCYCLE, 10.0);
        hourlyCosts.put(ParkingSpotType.COMPACT, 20.0);
        hourlyCosts.put(ParkingSpotType.LARGE, 30.0);
    }

    public double getCost(ParkingSpotType parkingSpotType) {
        return hourlyCosts.get(parkingSpotType);
    }
}
