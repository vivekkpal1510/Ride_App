package org.rideauthservice.ride_authservice.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.rideauthservice.ride_authservice.dto.*;
import org.rideauthservice.ride_authservice.repositories.DriverReposistory;
import org.rideauthservice.ride_authservice.repositories.PassengerRepository;
import org.rideauthservice.ride_authservice.service.AuthService;
import org.rideauthservice.ride_authservice.service.DriverDetailsServiceImpl;
import org.rideauthservice.ride_authservice.service.JwtService;
import org.rideauthservice.ride_authservice.service.UserDetailsServiceImpl;
import org.rideauthservice.ride_entityservice.models.Driver;
import org.rideauthservice.ride_entityservice.models.Passenger;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {



    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final AuthService authService;
    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final DriverDetailsServiceImpl driverDetailsServiceImpl;

    private AuthController(AuthService authService , @Lazy AuthenticationManager authenticationManager , JwtService jwtService ,DriverDetailsServiceImpl driverDetailsServiceImpl, UserDetailsServiceImpl userDetailsServiceImpl) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.authService = authService;
        this.driverDetailsServiceImpl = driverDetailsServiceImpl;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    @PostMapping("/signin/passenger")
    public ResponseEntity<?> signIn(@RequestBody AuthRequestDto authRequestDto, HttpServletResponse response) {
        System.out.println("Request received " + authRequestDto.getEmail() + " " + authRequestDto.getPassword());
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDto.getEmail(), authRequestDto.getPassword()));

        if(authentication.isAuthenticated()) {
            Passenger passenger = userDetailsServiceImpl.getPassengerByEmail(authRequestDto.getEmail());
            String jwtToken = jwtService.createToken(authRequestDto.getEmail() );
            System.out.println("JWT token: " + jwtToken);
            ResponseCookie cookie = ResponseCookie.from("JwtToken", jwtToken)
                    .httpOnly(true)
                    .secure(false)
                    .path("/")
                    .maxAge(7*24*3600)
                    .build();

            response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
            return ResponseEntity.ok(
                    AuthResponseDto.builder()
                            .success(true)
                            .user(passenger)
                            .build()
            );
        } else {
            System.out.println("Authentication failed");
            throw new UsernameNotFoundException("User not found");
        }
    }
    @PostMapping("/signup/passenger")
    public ResponseEntity<?> signUp(@RequestBody PassengerSignupRequestDto passengerSignupRequestDto) {
        PassengerDto response = authService.signUpPassenger(passengerSignupRequestDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/validate")
    public ResponseEntity<?> Validate(HttpServletRequest request) {
        System.out.println("Request received " + request.getRequestURI() + " " + request.getQueryString());
         Cookie[] cookei = request.getCookies();
         if(cookei != null) {
             for(Cookie cookie : cookei) {
                 String cookieName = cookie.getName();
                 String cookieValue = cookie.getValue();
                 System.out.println(cookieName + " " + cookieValue);
                 if(cookie.getName().equals("JwtToken")) {
                     String jwtToken = cookie.getValue();
                     return ResponseEntity.status(HttpStatus.OK).build();
                 }
             }
         }
         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/signup/driver")
    public ResponseEntity<?> signUpDriver(@RequestBody  DriverSignUpRequestDto driverSignUpRequestDto) {
        System.out.println("Request received " + driverSignUpRequestDto.getEmail());
        Driver driver = authService.signUpDriver(driverSignUpRequestDto);
        if(driver != null) {
            return ResponseEntity.ok(AuthResponseDto.builder().success(true).build());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/signin/driver")
    public ResponseEntity<?> signInDriver(@RequestBody AuthRequestDto authRequestDto,
                                          HttpServletResponse response) {

        System.out.println("Driver signin request for: " + authRequestDto.getEmail());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequestDto.getEmail(),
                        authRequestDto.getPassword()
                )
        );
        Driver driver = driverDetailsServiceImpl.getDriverByEmail(authRequestDto.getEmail());
            String jwtToken = jwtService.createToken(authRequestDto.getEmail());
            ResponseCookie cookie = ResponseCookie.from("JwtToken", jwtToken)
                    .httpOnly(true)
                    .secure(false) // change to true in production
                    .path("/")
                    .maxAge(7 * 24 * 3600)
                    .build();
            response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
            return ResponseEntity.ok(
                    AuthResponseDto.builder()
                            .success(true)
                            .user(driver)
                            .build()
            );

    }

}
