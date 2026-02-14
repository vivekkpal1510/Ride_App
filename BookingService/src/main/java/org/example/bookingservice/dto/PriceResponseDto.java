package org.example.bookingservice.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PriceResponseDto {

    private List<Feature> features;

    @Getter
    @Setter
    public static class Feature {
        private Properties properties;
    }

    @Getter
    @Setter
    public static class Properties {
        private double distance;
        private double time;
    }

    public double getTotalDistance() {
        return features.get(0).getProperties().getDistance();
    }

    public double getTotalTime() {
        return features.get(0).getProperties().getTime();
    }
}
