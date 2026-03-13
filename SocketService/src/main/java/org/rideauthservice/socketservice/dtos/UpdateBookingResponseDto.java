package org.rideauthservice.socketservice.dtos;


import lombok.*;
import org.rideauthservice.ride_entityservice.models.BookingStatus;

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
    private String otp ;

}
