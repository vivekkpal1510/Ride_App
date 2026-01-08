package org.rideauthservice.ride_authservice.controllers;

import org.rideauthservice.ride_authservice.dto.AuthRequestDto;
import org.rideauthservice.ride_authservice.dto.PassengerDto;
import org.rideauthservice.ride_authservice.dto.PassengerSignupRequestDto;
import org.rideauthservice.ride_authservice.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private AuthService authService;

    private AuthController(AuthService authService , AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.authService = authService;
    }
    @PostMapping("/signin/passenger")
    public ResponseEntity<?> signIn(@RequestBody AuthRequestDto authRequestDto) {
//        System.out.println("Request received " + authRequestDto.getEmail() + " " + authRequestDto.getPassword());
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDto.getEmail(), authRequestDto.getPassword()));
        if(authentication.isAuthenticated()){
            return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
        }
           throw   new UsernameNotFoundException("User not found");
    }

    @PostMapping("/signup/passenger")
    public ResponseEntity<?> signUp(@RequestBody PassengerSignupRequestDto passengerSignupRequestDto) {
        PassengerDto response = authService.signUpPassenger(passengerSignupRequestDto);
        return ResponseEntity.ok(response);
    }
}
