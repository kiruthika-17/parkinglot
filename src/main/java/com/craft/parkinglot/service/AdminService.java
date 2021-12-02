package com.craft.parkinglot.service;

import com.craft.parkinglot.Exception.DuplicateEntityFound;
import com.craft.parkinglot.Exception.InvalidParkingFloorException;
import com.craft.parkinglot.model.parking.Entrance;
import com.craft.parkinglot.model.parking.Exit;
import com.craft.parkinglot.model.parking.ParkingFloor;
import com.craft.parkinglot.model.parking.ParkingSpot;

import java.util.List;

public interface AdminService {
    void addParkingFloor(ParkingFloor parkingFloor) throws DuplicateEntityFound;

    void addEntrance(Entrance entrance);

    void addExit(Exit exit);

    void addParkingSpot(String floorId, ParkingSpot parkingSpot) throws InvalidParkingFloorException, DuplicateEntityFound;

    void addParkingSpotList(String floorId, List<ParkingSpot> parkingSpot) throws InvalidParkingFloorException;
}
