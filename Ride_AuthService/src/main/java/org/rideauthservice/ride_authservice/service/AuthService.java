package org.rideauthservice.ride_authservice.service;

import org.rideauthservice.ride_authservice.dto.PassengerDto;
import org.rideauthservice.ride_authservice.dto.PassengerSignupRequestDto;
import org.rideauthservice.ride_authservice.models.Passenger;
import org.rideauthservice.ride_authservice.repositories.PassengerRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final PassengerRepository passengerRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthService(PassengerRepository passengerRepository , BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.passengerRepository = passengerRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public PassengerDto signUpPassenger(PassengerSignupRequestDto passengerSignupRequestDto) {
        Passenger passenger = new Passenger().builder()
                .name(passengerSignupRequestDto.getName())
                .password(bCryptPasswordEncoder.encode(passengerSignupRequestDto.getPassword())) // has to be encrypted
                .email(passengerSignupRequestDto.getEmail())
                .phoneNumber(passengerSignupRequestDto.getPhoneNumber())
                .build();

        Passenger newPassenger =  passengerRepository.save(passenger);
        return PassengerDto.from(newPassenger);

    }
}
