//package com.driver.services.impl;
//
//import com.driver.model.Payment;
//import com.driver.model.PaymentMode;
//import com.driver.model.Reservation;
//import com.driver.model.Spot;
//import com.driver.repository.PaymentRepository;
//import com.driver.repository.ReservationRepository;
//import com.driver.repository.SpotRepository;
//import com.driver.services.PaymentService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class PaymentServiceImpl implements PaymentService {
//    @Autowired
//    ReservationRepository reservationRepository2;
//    @Autowired
//    PaymentRepository paymentRepository2;
//
//    @Autowired
//    SpotRepository spotRepository;
//
//    @Override
//    public Payment pay(Integer reservationId, int amountSent, String mode) throws Exception {
//        Payment payment=new Payment();
//
//        Reservation reservation=reservationRepository2.findById(reservationId).get();
//
//        Spot spot=reservation.getSpot();
//        int time=spot.getPricePerHour();
//        int cost=time*reservation.getNumberOfHours();
//        if(cost>amountSent){
//            throw new Exception("Insufficient Amount");
//        }
//
//        if(!mode.equalsIgnoreCase("cash") && !mode.equalsIgnoreCase("card") && !mode.equalsIgnoreCase("upi")) {
//            throw new Exception("Payment mode not detected");
//        }
//
//
//        if(mode.equalsIgnoreCase("cash")){
//            payment.setPaymentMode(PaymentMode.CASH);
//        }
//        if(mode.equalsIgnoreCase("upi")){
//            payment.setPaymentMode(PaymentMode.UPI);
//        }
//        if(mode.equalsIgnoreCase("card")){
//            payment.setPaymentMode(PaymentMode.CARD);
//        }
//
//        payment.setPaymentCompleted(true);
//        reservation.setPayment(payment);
//        payment.setReservation(reservation);
//        spot.setOccupied(false);
//        spotRepository.save(spot);
//        reservationRepository2.save(reservation);
//        paymentRepository2.save(payment);
//
//        return payment;
//    }
//
//
//}



package com.driver.services.impl;

import com.driver.model.Payment;
import com.driver.model.PaymentMode;
import com.driver.model.Reservation;
import com.driver.repository.PaymentRepository;
import com.driver.repository.ReservationRepository;
import com.driver.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    ReservationRepository reservationRepository2;
    @Autowired
    PaymentRepository paymentRepository2;

    @Override
    public Payment pay(Integer reservationId, int amountSent, String mode) throws Exception {

        Reservation reservation = reservationRepository2.findById(reservationId).get();

        int billPrice = reservation.getNumberOfHours()*reservation.getSpot().getPricePerHour();
        if(amountSent < billPrice){
            throw new Exception("Insufficient Amount");
        }
        else{

            if(mode.equalsIgnoreCase("cash") || mode.equalsIgnoreCase("card") || mode.equalsIgnoreCase("upi")){
                Payment payment = new Payment();
                if(mode.equalsIgnoreCase("cash")){
                    payment.setPaymentMode(PaymentMode.CASH);
                } else if (mode.equalsIgnoreCase("card")) {
                    payment.setPaymentMode(PaymentMode.CARD);
                }
                else payment.setPaymentMode(PaymentMode.UPI);

                payment.setPaymentCompleted(true);
                payment.setReservation(reservation);

                //Bidirectional
                reservation.setPayment(payment);

                reservationRepository2.save(reservation);

                return payment;
            }

            else{
                throw new Exception("Payment mode not detected");
            }
        }

    }
}
