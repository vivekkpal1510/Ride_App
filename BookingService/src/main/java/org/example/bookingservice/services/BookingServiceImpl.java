package org.example.bookingservice.services;


import org.example.bookingservice.apis.LocationServiceApi;
import org.example.bookingservice.apis.PricingApi;
import org.example.bookingservice.apis.SocketApi;
import org.example.bookingservice.dto.*;
import org.example.bookingservice.repositories.BookingRepository;
import org.example.bookingservice.repositories.DriverRepository;
import org.example.bookingservice.repositories.PassengerRepository;
import org.rideauthservice.ride_entityservice.models.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {

    double baseFare = 50;
    double perKmRate = 12;
    double perMinuteRate = 2;
    double minimumFare = 60;

    @Value("${geoapify.apikey}")
    private String apiKey;


    public final PricingApi pricingApi;

    private final PassengerRepository passengerRepository;
    private final BookingRepository bookingRepository;
    private final LocationServiceApi locationServiceApi;
    private final DriverRepository driverRepository;
    private final RestTemplate restTemplate;
    private final SocketApi socketApi;

    public BookingServiceImpl(PassengerRepository passengerRepository,
                              BookingRepository bookingRepository,
                              LocationServiceApi locationServiceApi,
                              DriverRepository driverRepository,
                              SocketApi socketApi,
                              PricingApi pricingApi) {
        this.pricingApi = pricingApi;
        this.passengerRepository = passengerRepository;
        this.bookingRepository = bookingRepository;
        this.restTemplate = new RestTemplate();
        this.locationServiceApi = locationServiceApi;
        this.socketApi = socketApi;
        this.driverRepository = driverRepository;
    }


    @Override
    public CreateBookingResponseDto createBooking(CreateBookingDto bookingDetails) {
        Optional<Passenger> passenger = passengerRepository.findById(bookingDetails.getPassengerId());
        Booking booking = Booking.builder()
                .bookingStatus(BookingStatus.ASSIGNING_DRIVER)
                .startLocation(bookingDetails.getStartLocation())
                .endLocation(bookingDetails.getEndLocation())
                .passenger(passenger.get())
                .build();
        Booking newBooking = bookingRepository.save(booking);

        NearbyDriversRequestDto request = NearbyDriversRequestDto.builder()
                .latitude(bookingDetails.getStartLocation().getLatitude())
                .longitude(bookingDetails.getStartLocation().getLongitude())
                .build();

        processNearbyDriversAsync(request, bookingDetails.getPassengerId(), newBooking.getId(), bookingDetails.getStartLocation(), bookingDetails.getEndLocation());
        return CreateBookingResponseDto.builder()
                .bookingId(newBooking.getId())
                .bookingStatus(newBooking.getBookingStatus().toString())
                .build();
    }

    @Override
    public UpdateBookingResponseDto updateBooking(UpdateBookingRequestDto bookingRequestDto, Long bookingId) {
        System.out.println("HII WORLD");
        Optional<Driver> driver = driverRepository.findById(bookingRequestDto.getDriverId());
        bookingRepository.updateBookingStatusAndDriverById(bookingId, BookingStatus.SCHEDULED, driver.get());
        Optional<Booking> booking = bookingRepository.findById(bookingId);

        Long passengerId = booking.get().getPassenger().getId() ;
        UpdateBookingResponseDto res =  UpdateBookingResponseDto.builder()
                .bookingId(bookingId)
                .status(booking.get().getBookingStatus())
                .plateNumber(driver.get().getCar().getPlateNumber())
                .carBrand(driver.get().getCar().getBrand())
                .phoneNumber(driver.get().getPhoneNumber())
                .driverName(driver.get().getName())
                .build();
        Call<Boolean> call = socketApi.bookingResponseHandler(res, passengerId);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()) {
                    System.out.println("Socket service notified");
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                System.out.println("Socket call failed: " + t.getMessage());
            }
        });
        return res;
    }

    private void processNearbyDriversAsync(NearbyDriversRequestDto requestDto, Long passengerId, Long bookingId, ExactLocation startLocation, ExactLocation endLocation) {
        Call<DriverLocationDto[]> call = locationServiceApi.getNearbyDrivers(requestDto);
        System.out.println(call.request().url() + " " + call.request().method() + " " + call.request().headers());

        call.enqueue(new Callback<DriverLocationDto[]>() {
            @Override
            public void onResponse(Call<DriverLocationDto[]> call, Response<DriverLocationDto[]> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<DriverLocationDto> driverLocations = Arrays.asList(response.body());
                    driverLocations.forEach(driverLocationDto -> {
                        System.out.println(driverLocationDto.getDriverId() + " " + "lat: " + driverLocationDto.getLatitude() + "long: " + driverLocationDto.getLongitude());
                    });
                    try {
                        raiseRideRequestAsync(RideRequestDto.builder().passengerId(passengerId).bookingId(bookingId).startLocation(startLocation).endLocation(endLocation).build());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    System.out.println("Request failed" + response.message());
                }
            }

            @Override
            public void onFailure(Call<DriverLocationDto[]> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void raiseRideRequestAsync(RideRequestDto requestDto) throws IOException {
        Call<Boolean> call = socketApi.raiseRideRequest(requestDto);
        System.out.println(call.request().url() + " " + call.request().method() + " " + call.request().headers());
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                System.out.println(response.isSuccessful());
                System.out.println(response.message());
                if (response.isSuccessful() && response.body() != null) {
                    Boolean result = response.body();
                    System.out.println("Driver response is " + result.toString());

                } else {
                    System.out.println("Request for ride failed " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    private PriceResponseDto getPrice(CreateBookingDto request) throws IOException {
        String waypoints =
                request.getStartLocation().getLatitude() + "," + request.getStartLocation().getLongitude()
                        + "|"
                        + request.getEndLocation().getLatitude() + "," + request.getEndLocation().getLongitude();

        System.out.println(waypoints);
        Call<PriceResponseDto> call =
                pricingApi.getPrice(waypoints, "drive", apiKey);

        return call.execute().body();
    }

    @Override
    public Double calculatePrice(CreateBookingDto request) throws IOException {

        PriceResponseDto response = getPrice(request);
        if (response == null) {
            // fall backoption if api stop working
            System.out.println("Request for price failed " + request.toString());
            return minimumFare;
        }
        double distanceMeters = response.getTotalDistance();
        double timeSeconds = response.getTotalTime();
        double distanceKm = distanceMeters / 1000.0;
        double timeMinutes = timeSeconds / 60.0;

        double fare =
                baseFare
                        + (distanceKm * perKmRate)
                        + (timeMinutes * perMinuteRate);
        return Math.max(fare, minimumFare);
    }


}