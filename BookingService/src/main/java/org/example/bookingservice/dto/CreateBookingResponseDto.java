package org.example.bookingservice.dto;


import lombok.*;
import org.rideauthservice.ride_entityservice.models.Driver;

import java.util.Optional;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookingResponseDto {
    private long bookingId;
    private String bookingStatus;
    private Optional<Driver> driver;

}