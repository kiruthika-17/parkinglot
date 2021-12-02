package com.craft.parkinglot.service;

import com.craft.parkinglot.Exception.FloorNotFound;
import com.craft.parkinglot.Exception.InvalidParkingFloorException;
import com.craft.parkinglot.Exception.ParkingSpotNotFound;
import com.craft.parkinglot.enums.VehicleType;
import com.craft.parkinglot.model.parking.ParkingSpot;
import com.craft.parkinglot.model.parking.ParkingTicket;
import com.craft.parkinglot.model.vehicle.Vehicle;

import java.util.Map;

public interface EntryParkingService {
    ParkingTicket bookParkingSpot(Vehicle vehicle) throws ParkingSpotNotFound;

    Boolean checkAvailability(VehicleType vehicleType);

    Object getSlotDetails(String floorId) throws InvalidParkingFloorException;

    Boolean isLotFull();

    Map<String, ParkingSpot> getVehicleDetails(String floorId) throws FloorNotFound;
}
