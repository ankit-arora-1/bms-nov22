package com.scaler.bookmyshow.repositories;

import com.scaler.bookmyshow.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Booking save(Booking booking);
}
