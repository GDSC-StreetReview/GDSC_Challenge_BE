package com.streetreview.street.entity;

import com.streetreview.street.dto.ResStreetListDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;

@Document(collection = "street")
@Getter
@NoArgsConstructor
public class Street {
    @Id
    private String id;
    private String streetAddress;
    private String streetName;
    private GeoJsonPoint location;
    private int likey = 0;
    @OneToMany
    private List<Tag> tagList;
    @CreatedDate
    private Date createdDate;
    @CreatedDate
    private Date updatedDate;

    @Builder
    public Street(String id, String streetAddress, String streetName, GeoJsonPoint location, int likey, List<Tag> tagList, Date createdDate, Date updatedDate) {
        this.id = id;
        this.streetAddress = streetAddress;
        this.streetName = streetName;
        this.location = location;
        this.likey = likey;
        this.tagList = tagList;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public ResStreetListDto toResStreetListDto(List<String> photoUrlList, int reviewCount) {
        return ResStreetListDto.builder()
                .streetName(streetName)
                .streetAddress(streetAddress)
                .x(location.getY())
                .y(location.getX())
                .photoUrlList(photoUrlList)
                .tagsList(tagList)
                .reviewCount(reviewCount)
                .likey(likey).build();
    }
}
