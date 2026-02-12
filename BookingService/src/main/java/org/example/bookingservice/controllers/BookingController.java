package org.example.bookingservice.controllers;


import org.example.bookingservice.dto.CreateBookingDto;
import org.example.bookingservice.dto.CreateBookingResponseDto;
import org.example.bookingservice.dto.UpdateBookingRequestDto;
import org.example.bookingservice.dto.UpdateBookingResponseDto;
import org.example.bookingservice.services.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/booking")

public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }
    @PostMapping
    public ResponseEntity<CreateBookingResponseDto> createBooking(@RequestBody CreateBookingDto createBookingDto) {

        return new ResponseEntity<>(bookingService.createBooking(createBookingDto), HttpStatus.CREATED);
    }

    @PostMapping("/{bookingId}")
    public ResponseEntity<UpdateBookingResponseDto> updateBooking(@RequestBody UpdateBookingRequestDto requestDto, @PathVariable Long bookingId) {
        System.out.println(bookingId +" " + requestDto +" "+ "hrll");
        return new ResponseEntity<>(bookingService.updateBooking(requestDto, bookingId), HttpStatus.OK);
    }


}