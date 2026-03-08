package org.rideauthservice.ride_authservice.service;

import org.rideauthservice.ride_authservice.helpers.AuthPassengerDetails;
import org.rideauthservice.ride_authservice.repositories.PassengerRepository;
import org.rideauthservice.ride_entityservice.models.Passenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private  PassengerRepository passengerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Passenger> passenger = passengerRepository.findPassengerByEmail(username);
        if (passenger.isPresent()) {
            return new AuthPassengerDetails(passenger.get());
        }else{
            throw new UsernameNotFoundException("can't find passengr " + username);
        }
    }

    public Passenger getPassengerByEmail(String email) {
        Optional<Passenger> passenger = passengerRepository.findPassengerByEmail(email);
        if (passenger.isPresent()) {
            Passenger p = passenger.get();
            p.setPassword(null);
            return p;
        }
        return null;
    }

}
