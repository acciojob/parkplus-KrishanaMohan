package com.driver.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "spot")

public class Spot {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    private SpotType spotType;
    private int pricePerHour;
    private Boolean occupied;
    @ManyToOne
    @JoinColumn
    private ParkingLot parkingLot;

    @OneToMany (mappedBy = "spot",cascade = CascadeType.ALL)
    private List<Reservation>reservationList;

    public Spot() {

        this.occupied=false;

    }

    public void setSpotType(SpotType spotType) {
        this.spotType = spotType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SpotType getSpotType() {
        return spotType;
    }

    public int getPricePerHours() {
        return pricePerHour;
    }

    public void setPricePerHours(int pricePerHours) {
        this.pricePerHour = pricePerHours;
    }

    public Boolean getOccupied() {
        return occupied;
    }

    public void setOccupied(Boolean occupied) {
        this.occupied = occupied;
    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    public List<Reservation> getReservationList() {
        return reservationList;
    }

    public void setReservationList(List<Reservation> reservationList) {
        this.reservationList = reservationList;
    }
}
