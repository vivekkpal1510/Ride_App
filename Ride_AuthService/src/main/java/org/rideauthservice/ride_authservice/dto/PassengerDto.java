package org.rideauthservice.ride_authservice.dto;

import lombok.*;
import org.rideauthservice.ride_authservice.models.Passenger;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PassengerDto {
    private String id;
    private String name ;
    private String email;
    private String password ;
    private String phoneNumber;
    private Date createdAt;

    public static PassengerDto from(Passenger passenger) {
        PassengerDto passengerDto = PassengerDto.builder()
                .id(passenger.getId().toString())
                .name(passenger.getName())
                .email(passenger.getEmail())
                .password(passenger.getPassword())
                .phoneNumber(passenger.getPhoneNumber())
                .createdAt(passenger.getCreatedAt())
        .build();
        return passengerDto;
    }
}
