package com.craft.parkinglot.controller;

import com.craft.parkinglot.Exception.FloorNotFound;
import com.craft.parkinglot.Exception.InvalidParkingFloorException;
import com.craft.parkinglot.Exception.ParkingSpotNotFound;
import com.craft.parkinglot.enums.VehicleType;
import com.craft.parkinglot.model.parking.ParkingTicket;
import com.craft.parkinglot.model.vehicle.Vehicle;
import com.craft.parkinglot.service.EntryParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/entry/parking")
public class EntryParkingController {

    @Autowired
    private EntryParkingService parkingService;

    @GetMapping( "/check-availability")
    public ResponseEntity<?> checkAvailability(@RequestBody VehicleType vehicleType){
        Boolean isAvailable = parkingService.checkAvailability(vehicleType);
        return new ResponseEntity<>(isAvailable, HttpStatus.OK);
    }

    @GetMapping( "/is-full")
    public ResponseEntity<?> isLotFull(){
        Boolean isNotAvailable = parkingService.isLotFull();
        return new ResponseEntity<>(isNotAvailable, HttpStatus.OK);
    }

    @PostMapping( "/book-slot")
    public ResponseEntity<?> bookParkingSpot(@RequestBody Vehicle vehicle) throws ParkingSpotNotFound {
        ParkingTicket parkingTicket = parkingService.bookParkingSpot(vehicle);
        return new ResponseEntity<>(parkingTicket, HttpStatus.OK);
    }

    @GetMapping( "/slot-details/{floorId}")
    public ResponseEntity<?> getSlotDetails(@PathVariable("floorId") String floorId) throws InvalidParkingFloorException {
        return new ResponseEntity<>(parkingService.getSlotDetails(floorId), HttpStatus.OK);
    }

    @GetMapping("/vehicle-list/{floorId}")
    public ResponseEntity<?> getVehicleDetails(@PathVariable("floorId") String floorId) throws FloorNotFound {
        return new ResponseEntity<>(parkingService.getVehicleDetails(floorId), HttpStatus.OK);
    }
}
