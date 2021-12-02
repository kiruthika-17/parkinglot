package com.craft.parkinglot.service;

import com.craft.parkinglot.Exception.ParkingSpotNotFound;
import com.craft.parkinglot.model.parking.ParkingTicket;

public interface ExitParkingService {
    void vacateSlot(ParkingTicket parkingTicket) throws ParkingSpotNotFound;
}
