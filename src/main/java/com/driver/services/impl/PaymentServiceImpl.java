package com.driver.services.impl;

import com.driver.model.Payment;
import com.driver.model.PaymentMode;
import com.driver.model.Reservation;
import com.driver.model.Spot;
import com.driver.repository.PaymentRepository;
import com.driver.repository.ReservationRepository;
import com.driver.repository.SpotRepository;
import com.driver.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    ReservationRepository reservationRepository2;
    @Autowired
    PaymentRepository paymentRepository2;

    @Autowired
    SpotRepository spotRepository;

    @Override
    public Payment pay(Integer reservationId, int amountSent, String mode) throws Exception {
        Payment payment=new Payment();

        Reservation reservation=reservationRepository2.findById(reservationId).get();
        if(reservation==null) {

            fail(reservationId);
            throw new Exception("Reservation is not Available");
        }

        Spot spot=reservation.getSpot();
        int time=spot.getPricePerHours();
        int cost=time*reservation.getNumberOfHours();
        if(cost>amountSent){
           fail(reservationId);
            throw new Exception("Insufficient Amount");
        }

        if(mode!="cash"&& mode!="upi" && mode!="card") {
            fail(reservationId);
            throw new Exception("Payment mode not detected");
        }

        payment.setPaymentComplete(true);
        reservation.setPayment(payment);
        payment.setReservation(reservation);
        spot.setOccupied(false);
        if(mode!="cash"){
            payment.setPaymentMode(PaymentMode.CASH);
        }
        if(mode!="upi"){
            payment.setPaymentMode(PaymentMode.UPI);
        }
        if(mode!="card"){
            payment.setPaymentMode(PaymentMode.CARD);
        }
        spotRepository.save(spot);
        reservationRepository2.save(reservation);
        paymentRepository2.save(payment);

        return payment;
    }
    public void fail(Integer reservationId){
        Payment payment=new Payment();
        payment.setPaymentComplete(false);
        Reservation reservation=reservationRepository2.findById(reservationId).get();
        reservation.setPayment(payment);
        payment.setReservation(reservation);
        paymentRepository2.save(payment);
        reservationRepository2.save(reservation);
    }

}