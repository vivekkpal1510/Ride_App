package org.rideauthservice.ride_authservice.repositories;

import org.rideauthservice.ride_entityservice.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
}
