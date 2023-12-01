package com.scaler.bookmyshow.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Booking extends BaseModel {
    @Enumerated(EnumType.ORDINAL)
    private BookingStatus bookingStatus;

    @ManyToMany
    private List<ShowSeat> showSeats;

    @ManyToOne
    private User user; // b:u -> 1:1 and u:b -> 1:m

    @ManyToOne
    private Show show;

    private int amount;

    @OneToMany
    private List<Payment> payments;
}
