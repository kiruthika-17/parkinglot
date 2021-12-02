package com.craft.parkinglot.controller;

import com.craft.parkinglot.model.parking.ParkingTicket;
import com.craft.parkinglot.model.parking.Payment;
import com.craft.parkinglot.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/make-payment")
    public ResponseEntity<?> makePayment(@RequestBody ParkingTicket parkingTicket){
        Payment payment = paymentService.makePayment(parkingTicket);
        return new ResponseEntity<>(payment, HttpStatus.OK);
    }
}
