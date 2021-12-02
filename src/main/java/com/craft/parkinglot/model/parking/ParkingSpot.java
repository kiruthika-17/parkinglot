package com.craft.parkinglot.model.parking;

import com.craft.parkinglot.enums.ParkingSpotType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ParkingSpot {
    private Integer parkingSpotId;
    private String parkingSpotLabel;
    private boolean isFree;
    private ParkingSpotType parkingSpotType;
    private String parkedVehicleId;

    public ParkingSpot(int parkingSpotId, ParkingSpotType parkingSpotType) {
        this.parkingSpotId = parkingSpotId;
        this.parkingSpotType = parkingSpotType;
    }

    public void assignVehicleToSpot(String vehicleId) {
        this.parkedVehicleId = vehicleId;
    }

    public void freeSpot() {
        this.isFree = true;
        this.parkedVehicleId = null;
    }
}
