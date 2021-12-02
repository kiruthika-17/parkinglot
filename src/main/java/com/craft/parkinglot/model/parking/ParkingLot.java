package com.craft.parkinglot.model.parking;

import com.craft.parkinglot.Exception.FloorNotFound;
import com.craft.parkinglot.Exception.ParkingSpotNotFound;
import com.craft.parkinglot.enums.ParkingSpotType;
import com.craft.parkinglot.enums.VehicleType;
import com.craft.parkinglot.model.account.Address;
import com.craft.parkinglot.model.vehicle.Vehicle;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class ParkingLot {

    private String parkingLotId;
    private Address address;

    private List<ParkingFloor> parkingFloors;
    private List<Entrance> entrances;
    private List<Exit> exits;

    public static ParkingLot INSTANCE = new ParkingLot();

    private ParkingLot() {
        this.parkingLotId = UUID.randomUUID().toString();
        parkingFloors = new ArrayList<>();
        entrances = new ArrayList<>();
        exits = new ArrayList<>();
    }

    public boolean canPark(VehicleType vehicleType) {
        for (ParkingFloor parkingFloor : parkingFloors) {
            if (parkingFloor.canPark(vehicleType))
                return true;
        }
        return false;
    }

    public boolean isFull() {
        int index = 0;
        BitSet bitSet = new BitSet();
        for (ParkingFloor parkingFloor : parkingFloors) {
            bitSet.set(index++, parkingFloor.isFloorFull());
        }
        return bitSet.cardinality() == bitSet.size();
    }

    public ParkingSpot getParkingSpot(Vehicle vehicle) throws ParkingSpotNotFound {
        for (ParkingFloor parkingFloor : ParkingLot.INSTANCE.getParkingFloors()) {
            ParkingSpot parkingSpot = parkingFloor.getSpot(vehicle);
            if (parkingSpot != null) {
                return parkingSpot;
            }
        }
        return null;
    }

    public List<ParkingSpot> vacateParkingSpot(ParkingTicket parkingTicket) throws ParkingSpotNotFound {
        for (ParkingFloor parkingFloor : ParkingLot.INSTANCE.getParkingFloors()) {
            List<ParkingSpot> parkingSpots = parkingFloor.vacateSpot(parkingTicket);
            if (parkingSpots != null && !parkingSpots.isEmpty())
                return parkingSpots;
        }
        return null;
    }

    public Map<String, ParkingSpot> listVehicleDetails(String floorId) throws FloorNotFound {
        for (ParkingFloor parkingFloor : parkingFloors) {
            if (parkingFloor.getFloorId().equals(floorId))
                return parkingFloor.getOccupiedParkingSpots();
        }
        throw new FloorNotFound("Floor not found!");
    }
}
