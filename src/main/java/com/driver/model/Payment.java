package com.driver.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "payment")
public class Payment {
    private int id;
    private Boolean paymentComplete;
    private PaymentMode paymentMode;

    public Payment() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getPaymentComplete() {
        return paymentComplete;
    }

    public void setPaymentComplete(Boolean paymentComplete) {
        this.paymentComplete = paymentComplete;
    }

    public PaymentMode getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(PaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    @OneToOne
    @JoinColumn
    private Reservation reservation;


}
