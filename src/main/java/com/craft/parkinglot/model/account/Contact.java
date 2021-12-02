package com.craft.parkinglot.model.account;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Contact {
    private String firstName;
    private String lastName;
    private Date dob;
    private String email;
    private String phone;
    private Address address;
}
