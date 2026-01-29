package org.example.bookingservice.services;


import org.example.bookingservice.dto.CreateBookingDto;
import org.example.bookingservice.dto.CreateBookingResponseDto;

public interface BookingService {

    CreateBookingResponseDto createBooking(CreateBookingDto bookingDetails);

}