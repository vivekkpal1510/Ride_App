package org.example.bookingservice.controllers;


import org.example.bookingservice.dto.CreateBookingDto;
import org.example.bookingservice.dto.CreateBookingResponseDto;
import org.example.bookingservice.dto.UpdateBookingRequestDto;
import org.example.bookingservice.dto.UpdateBookingResponseDto;
import org.example.bookingservice.services.BookingService;
import org.rideauthservice.ride_entityservice.models.ExactLocation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin(origins = "http://localhost:2220")
@RestController
@RequestMapping("/api/v1/booking")

public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public ResponseEntity<?> getPrice(
            @RequestParam Long passengerId,
            @RequestParam Double startLatitude,
            @RequestParam Double startLongitude,
            @RequestParam Double endLatitude,
            @RequestParam Double endLongitude
    ) throws IOException {

        CreateBookingDto request = CreateBookingDto.builder()
                .passengerId(passengerId)
                .startLocation(new ExactLocation(startLatitude, startLongitude))
                .endLocation(new ExactLocation(endLatitude, endLongitude))
                .build();

        double res = bookingService.calculatePrice(request);
        res = Math.round(res * 100.0) / 100.0;
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<CreateBookingResponseDto> createBooking(@RequestBody CreateBookingDto createBookingDto) {

        return new ResponseEntity<>(bookingService.createBooking(createBookingDto), HttpStatus.CREATED);
    }

    @PostMapping("/{bookingId}")
    public ResponseEntity<UpdateBookingResponseDto> updateBooking(@RequestBody UpdateBookingRequestDto requestDto, @PathVariable Long bookingId) {
        System.out.println(bookingId +" " + requestDto +" "+ "hrll");
        System.out.println("HELLO WORLD");
        return new ResponseEntity<>(bookingService.updateBooking(requestDto, bookingId), HttpStatus.OK);
    }

}