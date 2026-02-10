package org.example.bookingservice.apis;

import org.example.bookingservice.dto.RideRequestDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SocketApi {

    @POST("api/socket/newride")
    Call<Boolean> raiseRideRequest(@Body RideRequestDto requestDto);
}