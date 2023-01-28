package com.driver.services.impl;

import com.driver.model.*;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.ReservationRepository;
import com.driver.repository.SpotRepository;
import com.driver.repository.UserRepository;
import com.driver.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    UserRepository userRepository3;
    @Autowired
    SpotRepository spotRepository3;
    @Autowired
    ReservationRepository reservationRepository3;
    @Autowired
    ParkingLotRepository parkingLotRepository3;


    @Override
    public Reservation reserveSpot(Integer userId, Integer parkingLotId, Integer timeInHours, Integer numberOfWheels) throws Exception {
       ParkingLot parkingLot =parkingLotRepository3.findById(userId).get();
       User user=userRepository3.findById(userId).get();
       if(user==null || parkingLot==null)
           throw new Exception("Cannot make reservation");

       List<Spot>list=parkingLot.getSpotList();
      Boolean spaceAvailable=false;

      int price=Integer.MAX_VALUE;
      Spot spot=null;
          for (Spot spot1:list) {
              if (!spot1.getOccupied()) {
                  int newprice=timeInHours*spot1.getPricePerHour();
                  if (spot.getSpotType().equals(SpotType.TWO_WHEELER) && 2 >= numberOfWheels && price>newprice) {
                      price=newprice;
                      spaceAvailable = true;
                      spot=spot1;

                  }
                  if (spot.getSpotType().equals(SpotType.FOUR_WHEELER) && 4 >= numberOfWheels && price>newprice) {
                      price=newprice;
                      spaceAvailable = true;
                      spot=spot1;
                  }

                  if (spot.getSpotType().equals(SpotType.OTHERS) && price>newprice) {
                      price=newprice;
                      spaceAvailable = true;
                      spot=spot1;

                  }
              }
          }

       if(!spaceAvailable)
           throw new Exception("Cannot make reservation");

       spot.setOccupied(true);
       Reservation reservation=new Reservation(timeInHours);

       List<Reservation> reservationList=spot.getReservationList();

       reservationList.add(reservation);
       spot.setReservationList(reservationList);
       reservation.setSpot(spot);


        List<Reservation>reservationList1=user.getReservationList();

        reservationList1.add(reservation);
        user.setReservationList(reservationList1);
        reservation.setUser(user);

        userRepository3.save(user);
        spotRepository3.save(spot);
        reservationRepository3.save(reservation);

        return reservation;
    }
}
