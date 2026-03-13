package org.example.bookingservice.services;

import org.example.bookingservice.dto.RedisBookingModel;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


@Service
public class RedisBookingServiceImpl implements RedisBookingService {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisBookingServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    private String buildKey(String bookingId){
        return "ride_otp" + bookingId;
    }

    @Override
    public boolean addBooking(RedisBookingModel redisBookingModel, String bookingId) {
        try {
            String key = buildKey(bookingId);

            redisTemplate.opsForValue().set(
                    key,
                    redisBookingModel
            );

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Long getDriverId(String bookingId) {
        try {

            RedisBookingModel val =
                    (RedisBookingModel) redisTemplate.opsForValue().get(buildKey(bookingId));

            return val == null ? null : val.getDriverId();

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getOtp(String bookingId) {
        try {

            RedisBookingModel model =
                    (RedisBookingModel) redisTemplate.opsForValue().get(buildKey(bookingId));

            return model == null ? null : model.getOtp();

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean updateBooking(RedisBookingModel redisBookingModel, String bookingId) {
        try {
            redisTemplate.opsForValue().set(buildKey(bookingId), redisBookingModel);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void deleteBooking(String bookingId){
        redisTemplate.delete(buildKey(bookingId));
    }

}