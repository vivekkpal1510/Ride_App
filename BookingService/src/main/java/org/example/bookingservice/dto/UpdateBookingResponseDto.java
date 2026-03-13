package org.example.bookingservice.dto;

import lombok.*;
import org.rideauthservice.ride_entityservice.models.BookingStatus;
import org.rideauthservice.ride_entityservice.models.Car;
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
    private String driverName;
    private  String plateNumber;
    private String carBrand;
    private String carModel;
    private String phoneNumber;
}