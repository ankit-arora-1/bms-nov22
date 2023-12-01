package com.scaler.bookmyshow.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookMovieResponseDto {
    private int amount;
    private Long bookingId;
    private ResponseStatus responseStatus;
}
