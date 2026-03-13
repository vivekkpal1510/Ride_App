package org.example.bookingservice.services;

import org.example.bookingservice.dto.RedisBookingModel;



public interface RedisBookingService {
    boolean addBooking(RedisBookingModel redisBookingModel,String bookingId);
    Long getDriverId(String bookingId);
    String getOtp(String bookingId);
    boolean updateBooking(RedisBookingModel redisBookingModel,String bookingId);
    void deleteBooking(String bookingId);
}
