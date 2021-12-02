package com.craft.parkinglot.service.impl;

import com.craft.parkinglot.Exception.ParkingSpotNotFound;
import com.craft.parkinglot.enums.ParkingSpotType;
import com.craft.parkinglot.enums.VehicleType;
import com.craft.parkinglot.model.parking.HourlyCost;
import com.craft.parkinglot.model.parking.ParkingLot;
import com.craft.parkinglot.model.parking.ParkingSpot;
import com.craft.parkinglot.model.parking.ParkingTicket;
import com.craft.parkinglot.service.ExitParkingService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ExitParkingServiceImpl implements ExitParkingService {

    /**
     * vacate the booked slot
     *
     * @param parkingTicket the booked ticket
     * @throws ParkingSpotNotFound if spot is not found
     */
    @Override
    public void vacateSlot(ParkingTicket parkingTicket) throws ParkingSpotNotFound {
        List<ParkingSpot> parkingSpotList = ParkingLot.INSTANCE.vacateParkingSpot(parkingTicket);
        for(ParkingSpot parkingSpot : parkingSpotList)
           parkingTicket.setCharges(calculateCost(parkingTicket, parkingSpot.getParkingSpotType()));
    }

    /**
     * calculate cost on hourly basis and slot type
     *
     * @param parkingTicket  parking ticket
     * @param parkingSpotType  parking slot type
     * @return total cost based on hourly basis
     */
    private double calculateCost(ParkingTicket parkingTicket, ParkingSpotType parkingSpotType) {
        long duration = parkingTicket.getIssuedAt().getTime() - new Date().getTime();
        long hours = TimeUnit.MILLISECONDS.toHours(duration);
        if (hours == 0)
            hours = 1;
        double amount = hours * new HourlyCost().getCost(parkingSpotType);
        if(parkingTicket.getVehicleType().equals(VehicleType.CAR))
            return amount*5;
        return amount;
    }
}
