package org.example.bookingservice.dto;


import lombok.*;
import org.rideauthservice.ride_entityservice.models.ExactLocation;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookingDto {

    private Long passengerId;

    private ExactLocation startLocation;

    private ExactLocation endLocation;
}