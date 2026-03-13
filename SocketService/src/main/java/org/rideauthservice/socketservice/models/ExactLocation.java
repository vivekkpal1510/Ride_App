package org.rideauthservice.socketservice.models;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class ExactLocation {

    private Double latitude;
    private Double longitude;
}