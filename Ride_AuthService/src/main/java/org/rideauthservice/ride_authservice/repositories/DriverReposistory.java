package org.rideauthservice.ride_authservice.repositories;

import org.rideauthservice.ride_entityservice.models.Driver;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DriverReposistory extends PagingAndSortingRepository<Driver, Long> {
    Driver save(Driver driver);
    Optional<Driver> findDriverByEmail(String email);
}
