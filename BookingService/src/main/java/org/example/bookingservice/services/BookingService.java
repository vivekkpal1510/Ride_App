package org.example.bookingservice.services;


import org.example.bookingservice.dto.CreateBookingDto;
import org.example.bookingservice.dto.CreateBookingResponseDto;
import org.example.bookingservice.dto.UpdateBookingRequestDto;
import org.example.bookingservice.dto.UpdateBookingResponseDto;

import java.io.IOException;

public interface BookingService {

    CreateBookingResponseDto createBooking(CreateBookingDto bookingDetails);

    UpdateBookingResponseDto updateBooking(UpdateBookingRequestDto bookingRequestDto, Long bookingId);

    public Double calculatePrice(CreateBookingDto request) throws IOException;
}