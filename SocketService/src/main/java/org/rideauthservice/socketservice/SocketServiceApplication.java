package org.rideauthservice.socketservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EnableDiscoveryClient
@EntityScan("org.rideauthservice.ride_entityservice.models")
public class SocketServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SocketServiceApplication.class, args);
    }

}
