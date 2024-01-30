package com.streetreview.street.dto;


import com.streetreview.file.entity.Photo;
import com.streetreview.file.entity.PhotoDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ResStreetListDto {
    private String streetName;
    private String streetAddress;
    private Double x;
    private Double y;
    private List<String> photoList;
    @Builder
    public ResStreetListDto(String streetName, String streetAddress, Double x, Double y, List<String> photoUrlList) {
        this.streetName = streetName;
        this.streetAddress = streetAddress;
        this.x = x;
        this.y = y;
        this.photoList = photoUrlList;
    }
}
