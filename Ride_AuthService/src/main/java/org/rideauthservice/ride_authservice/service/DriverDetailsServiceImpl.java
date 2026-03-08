package org.rideauthservice.ride_authservice.service;

import org.rideauthservice.ride_authservice.helpers.AuthDriverDetails;
import org.rideauthservice.ride_authservice.helpers.AuthPassengerDetails;
import org.rideauthservice.ride_authservice.repositories.DriverReposistory;
import org.rideauthservice.ride_authservice.repositories.PassengerRepository;
import org.rideauthservice.ride_entityservice.models.Driver;
import org.rideauthservice.ride_entityservice.models.DriverApprovalStatus;
import org.rideauthservice.ride_entityservice.models.Passenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DriverDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private DriverReposistory driverReposistory;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Driver> driver = driverReposistory.findDriverByEmail(username);
        if (driver.isPresent() && driver.get().getDriverApprovalStatus() == DriverApprovalStatus.APPROVED) {
            return new AuthDriverDetails(driver.get());
        }else{
            throw new UsernameNotFoundException("can't find driver " + username);
        }
    }

    public Driver getDriverByEmail(String email) {
        Optional<Driver> driver = driverReposistory.findDriverByEmail(email);
        if (driver.isPresent()) {
            Driver d = driver.get();
            d.setPassword(null);
            return d;
        }
        return null;
    }

}
