package org.example.bookingservice.apis;

import org.example.bookingservice.dto.PriceResponseDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PricingApi {
    @GET("v1/routing")
    Call<PriceResponseDto> getPrice(
            @Query("waypoints") String waypoints,
            @Query("mode") String mode,
            @Query("apiKey") String apiKey
    );
}
