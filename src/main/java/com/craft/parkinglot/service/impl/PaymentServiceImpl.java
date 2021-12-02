package com.craft.parkinglot.service.impl;

import com.craft.parkinglot.model.parking.ParkingTicket;
import com.craft.parkinglot.model.parking.Payment;
import com.craft.parkinglot.service.PaymentService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Override
    public Payment makePayment(ParkingTicket parkingTicket) {

        Payment payment = new Payment(UUID.randomUUID().toString(),
                parkingTicket.getTicketNumber(), parkingTicket.getCharges());
        payment.makePayment();
        return payment;
    }
}
