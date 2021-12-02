package com.craft.parkinglot.service;

import com.craft.parkinglot.model.parking.ParkingTicket;
import com.craft.parkinglot.model.parking.Payment;

public interface PaymentService {
    Payment makePayment(ParkingTicket parkingTicket);
}
