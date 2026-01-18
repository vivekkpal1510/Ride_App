package org.rideauthservice.ride_locationservice.services;


import org.rideauthservice.ride_locationservice.dtos.DriverLocationDto;

import java.util.List;

public interface LocationService {

    Boolean saveDriverLocation(String driverId, Double latitude, Double Longitude);

    List<DriverLocationDto> getNearByDrivers(Double latitude, Double Longitude);

}