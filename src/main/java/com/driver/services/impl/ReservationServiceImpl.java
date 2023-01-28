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
       if(parkingLot==null)
           throw new Exception("Cannot make reservation");

       List<Spot>list=parkingLot.getSpotList();
      Boolean spaceAvailable=false;
      Spot spot=null;
      if(list!=null) {
          for (int i = 0; i < list.size(); i++) {
              spot = list.get(i);
              if (spot != null && !spot.getOccupied()) {
                  if (spot.getSpotType() == SpotType.FOUR_WHEELER && 4 >= numberOfWheels) {
                      spaceAvailable = true;
                      break;
                  }
                  if (spot.getSpotType() == SpotType.TWO_WHEELER && 2 >= numberOfWheels) {
                      spaceAvailable = true;
                      break;
                  }
                  if (spot.getSpotType() == SpotType.TWO_WHEELER) {
                      spaceAvailable = true;
                      break;
                  }
              }
          }
      }
       if(!spaceAvailable)
           throw new Exception("Cannot make reservation");

       spot.setOccupied(true);
       Reservation reservation=new Reservation(timeInHours);

       List<Reservation> reservationList=spot.getReservationList();
       if(reservationList==null)
           reservationList=new ArrayList<>();
       //bidirection mapping
       reservationList.add(reservation);
       spot.setReservationList(reservationList);
       reservation.setSpot(spot);


        User user=userRepository3.findById(userId).get();

        List<Reservation>reservationList1=user.getReservationList();
        if(reservationList1==null)
            reservationList1=new ArrayList<>();
        reservationList1.add(reservation);
        user.setReservationList(reservationList1);
        reservation.setUser(user);

        userRepository3.save(user);
        spotRepository3.save(spot);
        reservationRepository3.save(reservation);

        return reservation;
    }
}
