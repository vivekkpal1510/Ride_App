package org.rideauthservice.ride_locationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class RideLocationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RideLocationServiceApplication.class, args);
	}

}
