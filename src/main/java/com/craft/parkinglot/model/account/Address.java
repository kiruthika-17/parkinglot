package com.craft.parkinglot.model.account;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Address {
    private String address;
    private String street;
    private String city;
    private String state;
    private String country;
    private String pinCode;
}
