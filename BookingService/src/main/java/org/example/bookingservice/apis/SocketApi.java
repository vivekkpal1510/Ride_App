package org.example.bookingservice.apis;

import org.example.bookingservice.dto.RideRequestDto;
import org.example.bookingservice.dto.UpdateBookingRequestDto;
import org.example.bookingservice.dto.UpdateBookingResponseDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SocketApi {

    @POST("api/socket/newride")
    Call<Boolean> raiseRideRequest(@Body RideRequestDto requestDto);

    @POST("api/socket/rideResponse")
    Call<Boolean> bookingResponseHandler(
            @Body UpdateBookingResponseDto requestDto,
            @Query("passengerId") Long passengerId
    );
}