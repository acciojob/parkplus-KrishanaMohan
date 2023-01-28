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

        Spot spot=reservation.getSpot();
        int time=spot.getPricePerHour();
        int cost=time*reservation.getNumberOfHours();
        if(cost>amountSent){
          // fail(reservationId);
            throw new Exception("Insufficient Amount");
        }

        if(mode!="cash"&& mode!="upi" && mode!="card") {
          //  fail(reservationId);
            throw new Exception("Payment mode not detected");
        }


        if(mode!="cash"){
            payment.setPaymentMode(PaymentMode.CASH);
        }
        if(mode!="upi"){
            payment.setPaymentMode(PaymentMode.UPI);
        }
        if(mode!="card"){
            payment.setPaymentMode(PaymentMode.CARD);
        }

        payment.setPaymentCompleted(true);
        reservation.setPayment(payment);
        payment.setReservation(reservation);
      //  spot.setOccupied(false);
     //   spotRepository.save(spot);
        reservationRepository2.save(reservation);
       // paymentRepository2.save(payment);

        return payment;
    }
//    public void fail(Integer reservationId){
//        Payment payment=new Payment();
//        payment.setPaymentCompleted(false);
//        Reservation reservation=reservationRepository2.findById(reservationId).get();
//        reservation.setPayment(payment);
//        payment.setReservation(reservation);
//       // reservationRepository2.save(reservation);
//        paymentRepository2.save(payment);
//
//    }

}
