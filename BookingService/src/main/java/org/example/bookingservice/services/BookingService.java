package org.example.bookingservice.services;


import org.example.bookingservice.dto.*;

import java.io.IOException;

public interface BookingService {

    CreateBookingResponseDto createBooking(CreateBookingDto bookingDetails);

    UpdateBookingResponseDto updateBooking(UpdateBookingRequestDto bookingRequestDto, Long bookingId);

    public Double calculatePrice(CreateBookingDto request) throws IOException;
    boolean verifyOtp(OtpRequestDto request) ;
    void endRide(String id);
}