package com.craft.parkinglot.service.impl;

import com.craft.parkinglot.Exception.DuplicateEntityFound;
import com.craft.parkinglot.Exception.InvalidParkingFloorException;
import com.craft.parkinglot.model.parking.*;
import com.craft.parkinglot.service.AdminService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    /**
     * add parking floor
     *
     * @param parkingFloor the parking floor entity
     * @throws DuplicateEntityFound if floor is present already
     */
    @Override
    public void addParkingFloor(ParkingFloor parkingFloor) throws DuplicateEntityFound {
        Optional<ParkingFloor> floor =
                ParkingLot.INSTANCE.getParkingFloors().stream()
                        .filter(pF -> pF.getFloorId().equalsIgnoreCase(parkingFloor.getFloorId()))
                        .findFirst();
        if (floor.isPresent())
            throw new DuplicateEntityFound("Floor exists with same id!");
        ParkingLot.INSTANCE.getParkingFloors().add(parkingFloor);
    }

    /**
     * add entrance to the floor
     *
     * @param entrance the entrance entity
     */
    @Override
    public void addEntrance(Entrance entrance) {
        Optional<Entrance> panel =
                ParkingLot.INSTANCE.getEntrances().stream()
                        .filter(eP -> eP.getId().equalsIgnoreCase(entrance.getId())).findFirst();
        if (panel.isPresent())
            return;
        ParkingLot.INSTANCE.getEntrances().add(entrance);
    }

    /**
     * add exit to the floor
     *
     * @param exit the exit entity
     */
    @Override
    public void addExit(Exit exit) {
        Optional<Exit> panel =
                ParkingLot.INSTANCE.getExits().stream()
                        .filter(eP -> eP.getId().equalsIgnoreCase(exit.getId())).findFirst();
        if (panel.isPresent())
            return;
        ParkingLot.INSTANCE.getExits().add(exit);
    }

    /**
     * add parking spot to the specified floor
     *
     * @param parkingFloorId the floor id in which spot needs to be added
     * @param parkingSpot the parking spot
     * @throws InvalidParkingFloorException, if floor is not found
     * @throws DuplicateEntityFound if spot is present already
     */
    @Override
    public void addParkingSpot(String parkingFloorId, ParkingSpot parkingSpot)
            throws InvalidParkingFloorException, DuplicateEntityFound {
        Optional<ParkingFloor> floor =
                ParkingLot.INSTANCE.getParkingFloors().stream()
                        .filter(pF -> pF.getFloorId().equals(parkingFloorId))
                        .findFirst();
        if (!floor.isPresent())
            throw new InvalidParkingFloorException("Invalid floor");

        Optional<ParkingSpot> spot =
                floor.get().getParkingSpots().get(parkingSpot.getParkingSpotType())
                        .stream()
                        .filter(pS -> pS.getParkingSpotId().equals(parkingSpot.getParkingSpotId()))
                        .findFirst();
        if (spot.isPresent())
            throw new DuplicateEntityFound("Parking spot already exists!");

        floor.get().getParkingSpots().get(parkingSpot.getParkingSpotType())
                .add(parkingSpot);
    }

    /**
     * add parking spot list to the specified floor
     *
     * @param parkingFloorId the floor id in which spot needs to be added
     * @param parkingSpotList the parking spot list
     * @throws InvalidParkingFloorException if floor is not found
     */
    @Override
    public void addParkingSpotList(String parkingFloorId, List<ParkingSpot> parkingSpotList)
            throws InvalidParkingFloorException {
        Optional<ParkingFloor> floor =
                ParkingLot.INSTANCE.getParkingFloors().stream()
                        .filter(pF -> pF.getFloorId().equals(parkingFloorId))
                        .findFirst();
        if (!floor.isPresent())
            throw new InvalidParkingFloorException("Invalid floor");

        for(ParkingSpot parkingSpot : parkingSpotList){
            Optional<ParkingSpot> spot =
                    floor.get().getParkingSpots().get(parkingSpot.getParkingSpotType())
                            .stream()
                            .filter(pS -> pS.getParkingSpotId().equals(parkingSpot.getParkingSpotId()))
                            .findFirst();
            if (spot.isPresent())
                return;

            floor.get().getParkingSpots().get(parkingSpot.getParkingSpotType())
                    .add(parkingSpot);
        }
    }
}
