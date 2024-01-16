package com.streetreview.street.dto;


import com.streetreview.street.entity.Street;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReqStreetCreationDto {
    private String streetAddress;
    private String streetName;
    private Double x;
    private Double y;

    @Builder
    public ReqStreetCreationDto(String streetAddress, String streetName, Double x, Double y) {
        this.streetAddress = streetAddress;
        this.streetName = streetName;
        this.x = x;
        this.y = y;
    }

    public Street toStreetEntity() {
        return Street.builder()
                .streetAddress(streetAddress)
                .streetName(streetName)
                .x(x)
                .y(y).build();
    }
}
