package com.craft.parkinglot.model.parking;

import lombok.Getter;

@Getter
public class Entrance {
    private String id;

    public Entrance(String id) {
        this.id = id;
    }
}
