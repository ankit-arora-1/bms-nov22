package com.scaler.bookmyshow.dtos;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SecondaryRow;

import java.util.List;

@Getter
@Setter
public class BookMovieRequestDto {
    private List<Long> showSeatIds;
    private Long userId;
    private Long showId;
}
