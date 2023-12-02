package com.scaler.bookmyshow.controllers;

import com.scaler.bookmyshow.dtos.BookMovieRequestDto;
import com.scaler.bookmyshow.dtos.BookMovieResponseDto;
import com.scaler.bookmyshow.dtos.ResponseStatus;
import com.scaler.bookmyshow.models.Booking;
import com.scaler.bookmyshow.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Controller
public class BookingController {

    private BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public BookMovieResponseDto bookMovie(BookMovieRequestDto request) {
        BookMovieResponseDto bookMovieResponseDto = new BookMovieResponseDto();
        Booking booking;

        try {
            booking = bookingService.bookMovie(
                    request.getUserId(),
                    request.getShowSeatIds(),
                    request.getShowId()
            );

            bookMovieResponseDto.setBookingId(booking.getId());
            bookMovieResponseDto.setAmount(booking.getAmount());
            bookMovieResponseDto.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (Exception ex) {
            bookMovieResponseDto.setResponseStatus(ResponseStatus.FAILURE);
        }

        return bookMovieResponseDto;
    }
}
