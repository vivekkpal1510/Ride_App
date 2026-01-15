package org.rideauthservice.ride_authservice;

import org.rideauthservice.ride_entityservice.models.Passenger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
@EntityScan("org.rideauthservice.ride_entityservice.models")
public class RideAuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RideAuthServiceApplication.class, args);
    }

}
