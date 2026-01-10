package org.rideauthservice.ride_authservice.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Value;
import org.rideauthservice.ride_authservice.dto.AuthRequestDto;
import org.rideauthservice.ride_authservice.dto.AuthResponseDto;
import org.rideauthservice.ride_authservice.dto.PassengerDto;
import org.rideauthservice.ride_authservice.dto.PassengerSignupRequestDto;
import org.rideauthservice.ride_authservice.service.AuthService;
import org.rideauthservice.ride_authservice.service.JwtService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final AuthService authService;

    private AuthController(AuthService authService , AuthenticationManager authenticationManager , JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.authService = authService;
    }
    @PostMapping("/signin/passenger")
    public ResponseEntity<?> signIn(@RequestBody AuthRequestDto authRequestDto, HttpServletResponse response) {
        System.out.println("Request received " + authRequestDto.getEmail() + " " + authRequestDto.getPassword());
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDto.getEmail(), authRequestDto.getPassword()));
        if(authentication.isAuthenticated()) {
            String jwtToken = jwtService.createToken(authRequestDto.getEmail());

            ResponseCookie cookie = ResponseCookie.from("JwtToken", jwtToken)
                    .httpOnly(true)
                    .secure(false)
                    .path("/")
                    .maxAge(7*24*3600)
                    .build();

            response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
            return new ResponseEntity<>(AuthResponseDto.builder().success(true).build(), HttpStatus.OK);
        } else {
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

}
