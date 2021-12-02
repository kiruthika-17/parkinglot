package com.craft.parkinglot.service.impl;

import com.craft.parkinglot.Exception.FloorNotFound;
import com.craft.parkinglot.Exception.InvalidParkingFloorException;
import com.craft.parkinglot.Exception.ParkingSpotNotFound;
import com.craft.parkinglot.enums.ParkingSpotType;
import com.craft.parkinglot.enums.VehicleType;
import com.craft.parkinglot.model.parking.*;
import com.craft.parkinglot.model.vehicle.Vehicle;
import com.craft.parkinglot.service.EntryParkingService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EntryParkingServiceImpl implements EntryParkingService {

    /**
     * book spot based on vehicle type
     *
     * @param vehicle the vehicle object
     * @return generated parking ticket
     * @throws ParkingSpotNotFound if spot is unavailable
     */
    @Override
    public ParkingTicket bookParkingSpot(Vehicle vehicle) throws ParkingSpotNotFound {
        ParkingSpot parkingSpot = ParkingLot.INSTANCE.getParkingSpot(vehicle);
        if (parkingSpot == null)
            throw new ParkingSpotNotFound("Parking Spot not available!");
        return buildTicket(vehicle, parkingSpot);
    }

    @Override
    public Boolean checkAvailability(VehicleType vehicleType) {
        return ParkingLot.INSTANCE.canPark(vehicleType);
    }

    @Override
    public Boolean isLotFull(){
        return ParkingLot.INSTANCE.isFull();
    }

    @Override
    public Map<String, ParkingSpot> getVehicleDetails(String floorId) throws FloorNotFound {
        return ParkingLot.INSTANCE.listVehicleDetails(floorId);
    }

    private ParkingTicket buildTicket(Vehicle vehicle, ParkingSpot parkingSpot) {
        ParkingTicket parkingTicket = new ParkingTicket();
        parkingTicket.setIssuedAt(new Date());
        parkingTicket.setAllocatedSpotId(parkingSpot.getParkingSpotId());
        parkingTicket.setAllocatedSpotLabel(parkingSpot.getParkingSpotLabel());
        parkingTicket.setLicensePlateNumber(vehicle.getLicenseNo());
        parkingTicket.setVehicleType(vehicle.getVehicleType());
        parkingTicket.setTicketNumber(UUID.randomUUID().toString());
        return parkingTicket;
    }

    /**
     * get all slot details in given floor
     *
     * @param parkingFloorId the floor id for which slot details is needed
     * @return list of slot details
     * @throws InvalidParkingFloorException if floor is not found
     */
    @Override
    public List<VacantSlots> getSlotDetails(String parkingFloorId)
            throws InvalidParkingFloorException {
        Optional<ParkingFloor> floor =
                ParkingLot.INSTANCE.getParkingFloors().stream()
                        .filter(pF -> pF.getFloorId().equals(parkingFloorId))
                        .findFirst();
        if (!floor.isPresent())
            throw new InvalidParkingFloorException("Invalid floor");

        List<VacantSlots> vacantSlots = new ArrayList<>();
        Map<ParkingSpotType, PriorityQueue<ParkingSpot>> parkingSpots = floor.get().getParkingSpots();
        for(Map.Entry<ParkingSpotType, PriorityQueue<ParkingSpot>> parkingSpot : parkingSpots.entrySet()){
            VacantSlots vacantSlot = new VacantSlots();
            vacantSlot.setSlotType(parkingSpot.getKey());
            List<String> slotLabels = new ArrayList<>();
            if(parkingSpot.getValue() != null && !parkingSpot.getValue().isEmpty()){
                for(ParkingSpot spot : parkingSpot.getValue()){
                    slotLabels.add(spot.getParkingSpotLabel());
                }
                vacantSlot.setSlotLabels(slotLabels);
            }
            vacantSlots.add(vacantSlot);
        }
        return vacantSlots;
    }
}
