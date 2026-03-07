package org.rideauthservice.ride_authservice.service;

import jakarta.transaction.Transactional;
import org.rideauthservice.ride_authservice.dto.DriverSignUpRequestDto;
import org.rideauthservice.ride_authservice.dto.PassengerDto;
import org.rideauthservice.ride_authservice.dto.PassengerSignupRequestDto;

import org.rideauthservice.ride_authservice.repositories.CarRepository;
import org.rideauthservice.ride_authservice.repositories.DriverReposistory;
import org.rideauthservice.ride_authservice.repositories.PassengerRepository;
import org.rideauthservice.ride_entityservice.models.Car;
import org.rideauthservice.ride_entityservice.models.Driver;
import org.rideauthservice.ride_entityservice.models.DriverApprovalStatus;
import org.rideauthservice.ride_entityservice.models.Passenger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final PassengerRepository passengerRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final CarRepository carRepository;
    private final DriverReposistory driverReposistory;

    public AuthService(PassengerRepository passengerRepository , BCryptPasswordEncoder bCryptPasswordEncoder, CarRepository carRepository, DriverReposistory driverReposistory) {
        this.passengerRepository = passengerRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.carRepository = carRepository;
        this.driverReposistory = driverReposistory;
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

    @Transactional
    public Driver signUpDriver(DriverSignUpRequestDto driverSignUpRequestDto) {
        Car car = new Car().builder()
                .model(driverSignUpRequestDto.getModel())
                .brand(driverSignUpRequestDto.getBrand())
                .carType(driverSignUpRequestDto.getCarType())
                .plateNumber(String.valueOf(driverSignUpRequestDto.getPlateNumber()))
                .build();

        carRepository.save(car);

        Driver driver = new Driver().builder()
                .name(driverSignUpRequestDto.getName())
                .email(driverSignUpRequestDto.getEmail())
                .driverApprovalStatus(DriverApprovalStatus.PENDING)
                .phoneNumber(driverSignUpRequestDto.getPhoneNumber())
                .aadharCard(driverSignUpRequestDto.getAadhar())
                .password(bCryptPasswordEncoder.encode(driverSignUpRequestDto.getPassword()))
                .licenseNumber(driverSignUpRequestDto.getLisenceNumber())
                .car(car)
                .build();

        Driver newDriver = driverReposistory.save(driver);

        return newDriver;
    }

}
