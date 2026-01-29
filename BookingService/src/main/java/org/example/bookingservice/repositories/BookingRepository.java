package org.example.bookingservice.repositories;



import org.rideauthservice.ride_entityservice.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookingRepository extends JpaRepository<Booking, Long> {

}