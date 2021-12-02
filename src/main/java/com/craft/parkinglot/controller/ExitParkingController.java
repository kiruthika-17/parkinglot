package com.craft.parkinglot.controller;

import com.craft.parkinglot.Exception.ParkingSpotNotFound;
import com.craft.parkinglot.model.parking.ParkingTicket;
import com.craft.parkinglot.service.ExitParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/exit/parking")
public class ExitParkingController {

    @Autowired
    private ExitParkingService exitParkingService;

    @PostMapping( "/vacate-slot")
    public ResponseEntity<?> vacateSlot(@RequestBody ParkingTicket parkingTicket) throws ParkingSpotNotFound {
        exitParkingService.vacateSlot(parkingTicket);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
