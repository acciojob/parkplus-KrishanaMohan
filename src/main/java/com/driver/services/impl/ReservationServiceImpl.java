package com.driver.services.impl;

import com.driver.model.*;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.ReservationRepository;
import com.driver.repository.SpotRepository;
import com.driver.repository.UserRepository;
import com.driver.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
       for(int i=0;i<list.size();i++){
           spot=list.get(i);
           if(!spot.getOccupied()){
               if(spot.getSpotType()==SpotType.FOUR_WHEELER && 4>=numberOfWheels){
                  spaceAvailable=true;
                  break;
               }
                if(spot.getSpotType()==SpotType.TWO_WHEELER && 2>=numberOfWheels){
                   spaceAvailable=true;
                   break;
               }
                if (spot.getSpotType()==SpotType.TWO_WHEELER){
                   spaceAvailable=true;
                   break;
               }
           }
       }
       if(!spaceAvailable)
           throw new Exception("Cannot make reservation");

       spot.setOccupied(true);
       Reservation reservation=new Reservation(timeInHours);
       List<Reservation> reservationList=spot.getReservationList();
       reservationList.add(reservation);
       reservation.setSpot(spot);
        spotRepository3.save(spot);

        User user=userRepository3.findById(userId).get();
        List<Reservation>reservationList1=user.getReservationList();
        reservationList1.add(reservation);
        userRepository3.save(user);

        reservationRepository3.save(reservation);

        return reservation;
    }
}
