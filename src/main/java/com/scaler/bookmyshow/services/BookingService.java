package com.scaler.bookmyshow.services;

import com.scaler.bookmyshow.models.Booking;
import com.scaler.bookmyshow.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookingService {

    private UserRepository userRepository;

    @Autowired
    public BookingService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Booking bookMovie(Long userId, List<Long> seatsIds, Long showId) {
        /*
        ------ START lock here for this lecture
        * 1. Get the user with user id from DB
        * 2. Get the show details from DB
        * ------------ Start LOCK here ------------------
        * 3. get the show seats with the given show seats ids from DB
        * 4. check if the show seats are available
        * 5. If they are not available, throw an error
        * 6. If they are available, update the status to blocked and update the timestamp
        * 7. Update the show seats in DB
        * --------- Release lock here -------------------
        * 8. return
        -------- END lock here for this lecture ------------
        * */

        return null;
    }
}
