package com.craft.parkinglot.controller;

import com.craft.parkinglot.Exception.DuplicateEntityFound;
import com.craft.parkinglot.Exception.InvalidParkingFloorException;
import com.craft.parkinglot.model.parking.Entrance;
import com.craft.parkinglot.model.parking.Exit;
import com.craft.parkinglot.model.parking.ParkingFloor;
import com.craft.parkinglot.model.parking.ParkingSpot;
import com.craft.parkinglot.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/add/floor")
    public ResponseEntity<?> addParkingFloor(@RequestBody String floodId) throws DuplicateEntityFound {
        adminService.addParkingFloor(new ParkingFloor(floodId));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/add/entrance")
    public ResponseEntity<?> addEntrance(@RequestBody String id){
        adminService.addEntrance(new Entrance(id));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/add/exit")
    public ResponseEntity<?> addExit(@RequestBody String id){
        adminService.addExit(new Exit(id));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping( "/add/parking-spot/{floorId}")
    public ResponseEntity<?> addParkingSpot(@PathVariable("floorId") String floorId,
                                            @RequestBody ParkingSpot parkingSpot) throws InvalidParkingFloorException, DuplicateEntityFound {
        adminService.addParkingSpot(floorId, parkingSpot);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping( "/add/parking-spot-list/{floorId}")
    public ResponseEntity<?> addParkingSpotList(@PathVariable("floorId") String floorId,
                                            @RequestBody List<ParkingSpot> parkingSpot) throws InvalidParkingFloorException, DuplicateEntityFound {
        adminService.addParkingSpotList(floorId, parkingSpot);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
