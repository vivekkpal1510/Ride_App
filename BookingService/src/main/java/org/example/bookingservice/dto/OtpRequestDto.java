package org.example.bookingservice.dto;

import lombok.Data;

@Data
public class OtpRequestDto {
    String otp;
    String bookingId;
}
