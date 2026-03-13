package org.example.bookingservice.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RedisBookingModel {
    private String otp ;
    private Long driverId ;
}
