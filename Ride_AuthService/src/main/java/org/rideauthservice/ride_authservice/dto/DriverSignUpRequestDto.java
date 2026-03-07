package org.rideauthservice.ride_authservice.dto;

import lombok.*;
import org.rideauthservice.ride_entityservice.models.CarType;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DriverSignUpRequestDto {
    String email;
    String password;
    String name ;
    String phoneNumber;
    Date createdAt;
    String aadhar ;
    String colour ;
    String model ;
    String lisenceNumber ;
    String plateNumber ;
    String brand ;
    CarType  carType;

}
