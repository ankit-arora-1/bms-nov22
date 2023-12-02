package com.scaler.bookmyshow.services;

import com.scaler.bookmyshow.models.*;
import com.scaler.bookmyshow.repositories.BookingRepository;
import com.scaler.bookmyshow.repositories.ShowRepository;
import com.scaler.bookmyshow.repositories.ShowSeatRepository;
import com.scaler.bookmyshow.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    private UserRepository userRepository;
    private ShowRepository showRepository;
    private ShowSeatRepository showSeatRepository;
    private BookingRepository bookingRepository;

    private PriceCalculatorService priceCalculatorService;

    @Autowired
    public BookingService(UserRepository userRepository,
                          ShowRepository showRepository,
                          ShowSeatRepository showSeatRepository,
                          PriceCalculatorService priceCalculatorService,
                          BookingRepository bookingRepository) {
        this.userRepository = userRepository;
        this.showRepository = showRepository;
        this.showSeatRepository = showSeatRepository;
        this.priceCalculatorService = priceCalculatorService;
        this.bookingRepository = bookingRepository;
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


        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()) {
            throw new RuntimeException();
        }

        User bookedBy = userOptional.get();

        Optional<Show> showOptional = showRepository.findById(showId);
        if(showOptional.isEmpty()) {
            throw new RuntimeException(); // Create a specific expection later
        }

        Show show = showOptional.get();

        List<ShowSeat> showSeats = showSeatRepository.findAllById(seatsIds);

        for(ShowSeat showSeat: showSeats) {
            if(!(showSeat.getShowSeatStatus().equals(ShowSeatStatus.AVAILABLE) ||
                    (showSeat.getShowSeatStatus().equals(ShowSeatStatus.BLOCKED) &&
                            Duration.between(showSeat.getBlockedAt().toInstant(),
                                    new Date().toInstant()).toMinutes() > 15))) {
                throw new RuntimeException();
            }
        }

        List<ShowSeat> savedShowSeat = new ArrayList<>();
        for(ShowSeat showSeat: showSeats) {
            showSeat.setShowSeatStatus(ShowSeatStatus.BLOCKED);
            showSeat.setBlockedAt(new Date());
            savedShowSeat.add(showSeatRepository.save(showSeat));
        }

        Booking booking = new Booking();
        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setShowSeats(savedShowSeat);
        booking.setUser(bookedBy);
        booking.setShow(show);
        booking.setAmount(priceCalculatorService.calculatePrice(savedShowSeat, show));

        Booking savedBooking = bookingRepository.save(booking);


        return booking;
    }
}
