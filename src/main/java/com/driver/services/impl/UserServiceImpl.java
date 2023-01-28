package com.driver.services.impl;

import com.driver.model.Reservation;
import com.driver.model.Spot;
import com.driver.model.User;
import com.driver.repository.SpotRepository;
import com.driver.repository.UserRepository;
import com.driver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository4;

    @Autowired
    SpotRepository spotRepository;


    @Override
    public void deleteUser(Integer userId) {
        User user =userRepository4.findById(userId).get();
//        List<Reservation>list=user.getReservationList();
//        for(Reservation reservation:list){
//            Spot spot=reservation.getSpot();
//            spot.setOccupied(false);
//            spotRepository.save(spot);
//        }

        userRepository4.delete(user);

    }

    @Override
    public User updatePassword(Integer userId, String password) {
         User user=userRepository4.findById(userId).get();
         user.setPassword(password);
         return user;

    }

    @Override
    public void register(String name, String phoneNumber, String password) {
        User user=new User(name,phoneNumber,password);
        userRepository4.save(user);

    }
}