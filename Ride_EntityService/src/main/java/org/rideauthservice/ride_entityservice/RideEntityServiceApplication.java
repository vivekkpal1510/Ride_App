package org.rideauthservice.ride_entityservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class RideEntityServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RideEntityServiceApplication.class, args);
	}

}
