package org.rideauthservice.ride_entityservice.models;
import jakarta.persistence.Entity;
import lombok.*;

import java.security.SecureRandom;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OTP extends BaseModel {

    private String code;

    private String sentToNumber;

    public static OTP make(String phoneNumber) {

        SecureRandom random = new SecureRandom();
        int otp = 100000 + random.nextInt(900000);
        return OTP.builder().code(String.valueOf(otp)).sentToNumber(phoneNumber).build();
    }

}