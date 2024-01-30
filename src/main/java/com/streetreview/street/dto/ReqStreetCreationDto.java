package com.streetreview.street.dto;


import com.streetreview.street.entity.Street;
import com.streetreview.street.entity.Tag;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.util.List;

@Getter
@NoArgsConstructor
public class ReqStreetCreationDto {
    private String streetAddress;
    private String streetName;
    private Double x;
    private Double y;
    private List<Tag> tagList;

    @Builder
    public ReqStreetCreationDto(String streetAddress, String streetName, Double x, Double y, List<Tag> tagList) {
        this.streetAddress = streetAddress;
        this.streetName = streetName;
        this.x = x;
        this.y = y;
        this.tagList = tagList;
    }

    public Street toStreetEntity() {
        return Street.builder()
                .streetAddress(streetAddress)
                .streetName(streetName)
                .location(new GeoJsonPoint(y, x))
                .tagList(tagList).build();
    }
}
