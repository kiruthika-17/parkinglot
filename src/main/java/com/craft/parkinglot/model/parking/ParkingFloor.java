package com.craft.parkinglot.model.parking;

import com.craft.parkinglot.Exception.ParkingSpotNotFound;
import com.craft.parkinglot.enums.ParkingSpotType;
import com.craft.parkinglot.enums.VehicleType;
import com.craft.parkinglot.model.vehicle.Vehicle;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
public class ParkingFloor {
    @Setter
    private String floorId;
    private Map<ParkingSpotType, PriorityQueue<ParkingSpot>> parkingSpots = new HashMap<>();
    private Map<String, ParkingSpot> occupiedParkingSpots = new HashMap<>();

    private static final int maxNumberOfLargeSlotInARow = 10;

    private static final int maxConsecutiveSlotNeededForBus = 5;

    public ParkingFloor(String id) {
        this.floorId = id;
        parkingSpots.put(ParkingSpotType.MOTORCYCLE, new PriorityQueue<>(new SlotComparator()));
        parkingSpots.put(ParkingSpotType.COMPACT, new PriorityQueue<>(new SlotComparator()));
        parkingSpots.put(ParkingSpotType.LARGE, new PriorityQueue<>(new SlotComparator()));
    }

    public boolean isFloorFull() {
        BitSet fullBitSet = new BitSet();
        int bitIndex = 0;
        for (Map.Entry<ParkingSpotType, PriorityQueue<ParkingSpot>> entry : parkingSpots.entrySet()) {
            if (entry.getValue().size() == 0) {
                fullBitSet.set(bitIndex++);
            } else {
                break;
            }
        }
        return fullBitSet.cardinality() == fullBitSet.size();
    }

    public boolean canPark(VehicleType vehicleType) {
        switch (vehicleType){
            case MOTORCYCLE:
                return parkingSpots.get(ParkingSpotType.MOTORCYCLE).size() > 0 ||
                        parkingSpots.get(ParkingSpotType.COMPACT).size() > 0 ||
                        parkingSpots.get(ParkingSpotType.LARGE).size() > 0;
            case CAR:
                return parkingSpots.get(ParkingSpotType.COMPACT).size() > 0 ||
                        parkingSpots.get(ParkingSpotType.LARGE).size() > 0;
            case BUS:
                return Objects.nonNull(checkAvailableSlotForBus(null));
        }
        return false;
    }

    public List<ParkingSpot> vacateSpot(ParkingTicket parkingTicket) throws ParkingSpotNotFound {
        List<ParkingSpot> parkingSpots = new ArrayList<>();
        Integer parkingSlotId = parkingTicket.getAllocatedSpotId();
        if(parkingTicket.getVehicleType().equals(VehicleType.BUS)){
            for(int i=0; i<5; i++){
                ParkingSpot parkingSpot = occupiedParkingSpots.remove(parkingSlotId.toString());
                addSpotToAvailableList(parkingSpot);
                parkingSlotId += 1;
                parkingSpots.add(parkingSpot);
            }
            return parkingSpots;
        }else{
            ParkingSpot parkingSpot = occupiedParkingSpots.remove(parkingSlotId.toString());
            addSpotToAvailableList(parkingSpot);
            parkingSpots.add(parkingSpot);
        }
        return parkingSpots;
    }

    private void addSpotToAvailableList(ParkingSpot parkingSpot) {
        if (parkingSpot != null) {
            parkingSpot.freeSpot();
            parkingSpots.get(parkingSpot.getParkingSpotType()).add(parkingSpot);
        }
    }

    public synchronized ParkingSpot getSpot(Vehicle vehicle) throws ParkingSpotNotFound {
        if (!canPark(vehicle.getVehicleType()))
            throw new ParkingSpotNotFound("Slot Unavailable!");
        return getSpotByType(vehicle);
    }

    public ParkingSpot getSpotByType(Vehicle vehicle){
        ParkingSpot bookedSpot = null;
        PriorityQueue<ParkingSpot> smallSpots = parkingSpots.get(ParkingSpotType.MOTORCYCLE);
        PriorityQueue<ParkingSpot> compactSpots = parkingSpots.get(ParkingSpotType.COMPACT);
        PriorityQueue<ParkingSpot> largeSpots = parkingSpots.get(ParkingSpotType.LARGE);

        if(vehicle.getVehicleType().equals(VehicleType.MOTORCYCLE)){
            bookedSpot =  smallSpots.size()>0 ? smallSpots.poll() :
                    compactSpots.size()>0 ? compactSpots.poll() : largeSpots.poll();
            addSpotToBookedSpotList(bookedSpot, vehicle);
        }
        else if(vehicle.getVehicleType().equals(VehicleType.CAR)){
            bookedSpot =  compactSpots.size()>0 ? compactSpots.poll() : largeSpots.poll();
            addSpotToBookedSpotList(bookedSpot, vehicle);
        }
        else if(vehicle.getVehicleType().equals(VehicleType.BUS)){
            bookedSpot =  checkAvailableSlotForBus(vehicle);
        }

        return bookedSpot;
    }

    private void addSpotToBookedSpotList(ParkingSpot bookedSpot, Vehicle vehicle) {
        bookedSpot.setFree(false);
        bookedSpot.setParkedVehicleId(vehicle.getLicenseNo());
        occupiedParkingSpots.put(bookedSpot.getParkingSpotId().toString(), bookedSpot);
    }

    private ParkingSpot checkAvailableSlotForBus(Vehicle vehicle) {
        PriorityQueue<ParkingSpot> queue = parkingSpots.get(ParkingSpotType.LARGE);
        List<ParkingSpot> spots = getAvailableSlotList(queue);

        int index,len=1, start, end;
        ParkingSpot startSlot=null;
        // Finding 5 consecutive large slots in linear time
        for(index=1; index<=spots.size(); index++){
            // Handling edge cases, consecutive slots should be in same row
            if(len >= 5){
                start = spots.get(index-maxConsecutiveSlotNeededForBus).getParkingSpotId();
                end = spots.get(index-1).getParkingSpotId();
                if((start%maxNumberOfLargeSlotInARow < end%maxNumberOfLargeSlotInARow &&
                        end%maxNumberOfLargeSlotInARow >= maxConsecutiveSlotNeededForBus) || end%maxNumberOfLargeSlotInARow==0){
                    startSlot = spots.get(index-len);
                    break;
                }
            }

            if(index==spots.size() || spots.get(index).getParkingSpotId() - spots.get(index-1).getParkingSpotId() != 1)
                len=1;
            else
                len++;
        }

        // Removing slots from available slots queue and adding it in booked slots queue
        if(Objects.nonNull(startSlot) && Objects.nonNull(vehicle))
         for(int j=index-maxConsecutiveSlotNeededForBus; j<=index-1 && j<spots.size(); j++){
            ParkingSpot spot = spots.get(j);
            queue.remove(spot);
            addSpotToBookedSpotList(spot, vehicle);
         }

       return startSlot;
    }

    private List<ParkingSpot>  getAvailableSlotList(PriorityQueue<ParkingSpot> queue) {
        Iterator<ParkingSpot> iterator = queue.iterator();
        List<ParkingSpot> spots = new ArrayList<>();
        while (iterator.hasNext()){
            ParkingSpot parkingSpot = iterator.next();
            spots.add(parkingSpot);
        }
        spots.sort(Comparator.comparingInt(ParkingSpot::getParkingSpotId));
        return spots;
    }

}


class SlotComparator implements Comparator<ParkingSpot>{
    @Override
    public int compare(ParkingSpot s1, ParkingSpot s2) {
        if(s1.getParkingSpotId() > s2.getParkingSpotId())
            return 1;
        else if(s1.getParkingSpotId() < s2.getParkingSpotId())
            return -1;
        return 0;
    }
}
