package com.craft.parkinglot.model.parking;

import com.craft.parkinglot.enums.VehicleType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
public class ParkingTicket {
    private String ticketNumber;
    private String licensePlateNumber;
    private Integer allocatedSpotId;
    private String allocatedSpotLabel;
    private VehicleType vehicleType;
    private Date issuedAt;
    private double charges;
}
