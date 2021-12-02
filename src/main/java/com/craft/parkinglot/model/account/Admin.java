package com.craft.parkinglot.model.account;

import com.craft.parkinglot.Exception.InvalidParkingFloorException;
import com.craft.parkinglot.enums.ParkingSpotType;
import com.craft.parkinglot.model.parking.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;

public class Admin extends Account{

    public void addParkingFloor(ParkingFloor parkingFloor) {
//        Optional<ParkingFloor> floor =
//                ParkingLot.INSTANCE.getParkingFloors().stream()
//                        .filter(pF -> pF.getFloorId().equalsIgnoreCase(parkingFloor.getFloorId()))
//                        .findFirst();
//        if (floor.isPresent())
//            return;
//        ParkingLot.INSTANCE.getParkingFloors().add(parkingFloor);
    }

    public void addEntrance(Entrance entrance) {
        Optional<Entrance> panel =
                ParkingLot.INSTANCE.getEntrances().stream()
                        .filter(eP -> eP.getId().equalsIgnoreCase(entrance.getId())).findFirst();
        if (panel.isPresent())
            return;
        ParkingLot.INSTANCE.getEntrances().add(entrance);
    }

    public void addExit(Exit exit) {
        Optional<Exit> panel =
                ParkingLot.INSTANCE.getExits().stream()
                        .filter(eP -> eP.getId().equalsIgnoreCase(exit.getId())).findFirst();
        if (panel.isPresent())
            return;
        ParkingLot.INSTANCE.getExits().add(exit);
    }

    public void addParkingSpot(String parkingFloorId, ParkingSpot parkingSpot)
            throws InvalidParkingFloorException {
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
            return;

        floor.get().getParkingSpots().get(parkingSpot.getParkingSpotType())
                .add(parkingSpot);
    }

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

    public void getSlotDetails(String parkingFloorId)
            throws InvalidParkingFloorException {
        Optional<ParkingFloor> floor =
                ParkingLot.INSTANCE.getParkingFloors().stream()
                        .filter(pF -> pF.getFloorId().equals(parkingFloorId))
                        .findFirst();
        if (!floor.isPresent())
            throw new InvalidParkingFloorException("Invalid floor");
        Map<ParkingSpotType, PriorityQueue<ParkingSpot>> parkingSpots = floor.get().getParkingSpots();
        for(Map.Entry<ParkingSpotType, PriorityQueue<ParkingSpot>> parkingSpot : parkingSpots.entrySet()){
           System.out.println(parkingSpot.getKey()+" Size: "+parkingSpot.getValue().size());
           for(ParkingSpot spot : parkingSpot.getValue()){
               System.out.print(spot.getParkingSpotId()+" ");
           }
           System.out.println();
        }
    }
}
