package org.rideauthservice.ride_locationservice.services;

import org.rideauthservice.ride_locationservice.dtos.DriverLocationDto;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RedisLocationServiceImpl implements LocationService {

    private static final String KEY_PREFIX = "driver_location";
    private static final Double SEARCH_RADIUS = 5.0;
    private StringRedisTemplate redisTemplate;

    public RedisLocationServiceImpl(StringRedisTemplate stringRedisTemplate) {
        this.redisTemplate = stringRedisTemplate;
    }


    @Override
    public Boolean saveDriverLocation(String driverId, Double latitude, Double Longitude) {
        GeoOperations<String, String> geoOps = redisTemplate.opsForGeo();
        geoOps.add(KEY_PREFIX , new RedisGeoCommands.GeoLocation<>(driverId,new Point(latitude,Longitude)));
        return true;

    }

    @Override
    public List<DriverLocationDto> getNearByDrivers(Double latitude, Double Longitude) {
        GeoOperations<String, String> geoOps = redisTemplate.opsForGeo();
        Distance radius = new Distance(SEARCH_RADIUS, Metrics.KILOMETERS);
        Circle within = new Circle(new Point(latitude, Longitude), radius);
        GeoResults<RedisGeoCommands.GeoLocation<String>> results = geoOps.radius(KEY_PREFIX, within);
        List<DriverLocationDto> drivers = new ArrayList<>();
        for(GeoResult<RedisGeoCommands.GeoLocation<String>> result : results) {
            Point point = geoOps.position(KEY_PREFIX, result.getContent().getName()).get(0);
            DriverLocationDto driverLocation = DriverLocationDto.builder()
                    .driverId(result.getContent().getName())
                    .latitude(point.getX())
                    .longitude(point.getY())
                    .build();
            drivers.add(driverLocation);
        }
        return drivers;
    }
}
