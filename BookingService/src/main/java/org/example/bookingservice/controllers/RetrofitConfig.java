package org.example.bookingservice.controllers;


import com.netflix.discovery.EurekaClient;
import okhttp3.OkHttpClient;
import org.example.bookingservice.apis.LocationServiceApi;
import org.example.bookingservice.apis.SocketApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Configuration
public class RetrofitConfig {
    @Autowired
    private EurekaClient eurekaClient;


    private String getServiceUrl(String serviceName) {
        return eurekaClient.getNextServerFromEureka(serviceName, false).getHomePageUrl();
    }

    @Bean
    public LocationServiceApi locationServiceApi() {
        return new Retrofit.Builder()
                .baseUrl(getServiceUrl("RIDE_LOCATIONSERVICE"))
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient.Builder().build())
                .build()
                .create(LocationServiceApi.class);
    }

    @Bean
    public SocketApi socketApi() {
        String serviceUrl = getServiceUrl("SOCKETSERVICE");
        System.out.println("Service url for socket" + serviceUrl);
        return new Retrofit.Builder()
                .baseUrl(serviceUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient.Builder().build())
                .build()
                .create(SocketApi.class);
    }

}