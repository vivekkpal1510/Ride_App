package org.rideauthservice.socketservice.controller;


import org.rideauthservice.socketservice.dtos.RideRequestDto;
import org.rideauthservice.socketservice.dtos.RideResponseDto;
import org.rideauthservice.socketservice.dtos.UpdateBookingRequestDto;
import org.rideauthservice.socketservice.dtos.UpdateBookingResponseDto;
import org.rideauthservice.socketservice.producers.KafkaProducerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/socket")
public class DriverRequestController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final RestTemplate restTemplate;
    private final KafkaProducerService kafkaProducerService;


    public DriverRequestController(SimpMessagingTemplate simpMessagingTemplate, KafkaProducerService kafkaProducerService) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.restTemplate = new RestTemplate();
        this.kafkaProducerService = kafkaProducerService;
    }

    @GetMapping
    public Boolean help() {
        kafkaProducerService.publishMessage("sample-topic", "Hello");
        return true;
    }

    @PostMapping("/newride")
    @CrossOrigin(originPatterns = "*")
    public ResponseEntity<Boolean> raiseRideRequest(@RequestBody RideRequestDto requestDto) {
        System.out.println("request for rides received");
        System.out.println("requestDto: " + requestDto.getEndLocation().getLatitude());
        sendDriversNewRideRequest(requestDto);
        System.out.println("Req completed");
        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }

    public void sendDriversNewRideRequest(RideRequestDto requestDto) {
        System.out.println("Executed periodic function");
        // TODO: Ideally the request should only go to nearby drivers, but for simplicity we send it everyone
        simpMessagingTemplate.convertAndSend("/topic/rideRequest", requestDto);
    }

    @MessageMapping("/rideResponse/{userId}")
    public synchronized void rideResponseHandler(@DestinationVariable String userId, RideResponseDto rideResponseDto) {
        System.out.println("Received ride response: " + rideResponseDto);
        System.out.println(rideResponseDto.getResponse() +" "+userId);
        UpdateBookingRequestDto requestDto = UpdateBookingRequestDto.builder()
                .driverId(Long.parseLong(userId))
                .status("SCHEDULED")
                .build();
        System.out.println("ffff");
        ResponseEntity<UpdateBookingResponseDto> result = this.restTemplate.postForEntity("http://localhost:8084/api/v1/booking/" + rideResponseDto.bookingId, requestDto, UpdateBookingResponseDto.class);
        System.out.println(result.getStatusCode());
    }

    @PostMapping("/rideResponse")
    public synchronized ResponseEntity<Boolean> bookingResponseHandler(
            @RequestBody UpdateBookingResponseDto responseDto,
            @RequestParam Long passengerId
    ) {
        System.out.println(responseDto.getDriverName() + " sending info to user "+ passengerId);
        simpMessagingTemplate.convertAndSend("/topic/response/" + passengerId, responseDto);
        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }

}
