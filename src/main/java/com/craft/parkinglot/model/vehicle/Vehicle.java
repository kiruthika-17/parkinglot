package com.craft.parkinglot.model.vehicle;

import com.craft.parkinglot.enums.VehicleType;
import com.craft.parkinglot.model.parking.ParkingTicket;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Vehicle {
    private String licenseNo;
    private VehicleType vehicleType;
    private ParkingTicket parkingTicket;

    public Vehicle(String licenseNo, VehicleType vehicleType) {
        this.licenseNo = licenseNo;
        this.vehicleType = vehicleType;
    }
}
