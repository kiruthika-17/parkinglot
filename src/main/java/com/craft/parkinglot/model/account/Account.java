package com.craft.parkinglot.model.account;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter 
public abstract class Account {
    private String id;
    private String userName;
    private String password;
    private Date createdAt;
    private Date modifiedAt;
    private Contact contact;

}
