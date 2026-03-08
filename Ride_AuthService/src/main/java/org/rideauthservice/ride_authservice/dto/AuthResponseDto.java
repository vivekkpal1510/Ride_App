package org.rideauthservice.ride_authservice.dto;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDto {
    public Boolean success;
    public Object user;
}