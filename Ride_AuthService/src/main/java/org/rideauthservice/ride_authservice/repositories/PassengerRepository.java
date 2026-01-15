package org.rideauthservice.ride_authservice.repositories;

//import org.rideauthservice.ride_authservice.models.Passenger;
import org.rideauthservice.ride_entityservice.models.Passenger;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PassengerRepository extends PagingAndSortingRepository<Passenger, Long> {

    Passenger save(Passenger passenger);

    Optional<Passenger> findPassengerByEmail(String username);
}
