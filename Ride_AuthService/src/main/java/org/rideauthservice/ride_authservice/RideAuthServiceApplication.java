package org.rideauthservice.ride_authservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class RideAuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RideAuthServiceApplication.class, args);
    }

}
