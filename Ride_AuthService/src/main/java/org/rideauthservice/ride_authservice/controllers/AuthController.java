package org.rideauthservice.ride_authservice.controllers;

import org.rideauthservice.ride_authservice.dto.PassengerDto;
import org.rideauthservice.ride_authservice.dto.PassengerSignupRequestDto;
import org.rideauthservice.ride_authservice.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private AuthService authService;

    private AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup/passenger")
    public ResponseEntity<?> signup(@RequestBody PassengerSignupRequestDto passengerSignupRequestDto) {
        PassengerDto response = authService.signUpPassenger(passengerSignupRequestDto);
        return ResponseEntity.ok(response);
    }
}
