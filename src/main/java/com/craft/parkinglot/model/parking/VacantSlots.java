package com.craft.parkinglot.model.parking;

import com.craft.parkinglot.enums.ParkingSpotType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class VacantSlots {
    private ParkingSpotType slotType;
    private List<String> slotLabels;
}
