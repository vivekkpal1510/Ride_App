package org.rideauthservice.socketservice.dtos;


import lombok.*;
import org.rideauthservice.ride_entityservice.models.BookingStatus;
import org.rideauthservice.ride_entityservice.models.Driver;

import java.util.Optional;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBookingResponseDto {

    private Long bookingId;
    private BookingStatus status;
    private Optional<Driver> driver;
}