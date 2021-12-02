package com.craft.parkinglot.model.parking;

import com.craft.parkinglot.enums.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
public class Payment {
    private String id;
    private String ticketId;
    private double amount;

    @Setter
    private Date initiatedDate;
    @Setter
    private Date completedDate;
    @Setter
    private PaymentStatus paymentStatus;

    public Payment(String id, String ticketId, double amount) {
        this.id = id;
        this.ticketId = ticketId;
        this.amount = amount;
    }

    public void makePayment() {
        this.initiatedDate = new Date();
        this.paymentStatus = PaymentStatus.SUCCESS;
        this.completedDate = new Date();
    }
}
