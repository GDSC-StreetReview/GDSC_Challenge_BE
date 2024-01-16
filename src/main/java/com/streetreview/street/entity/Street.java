package com.streetreview.street.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Document(collection = "street")
@Entity
@Getter
@NoArgsConstructor
public class Street {
    @Id
    private Long streetId;
    private String streetAddress;
    private String StreetName;
    private Double x;
    private Double y;
    @CreatedDate
    private Date createdDate;
    @CreatedDate
    private Date updatedDate;


    @Builder
    public Street(Long streetId, String streetAddress, String streetName, Double x, Double y, Date createdDate, Date updatedDate) {
        this.streetId = streetId;
        this.streetAddress = streetAddress;
        StreetName = streetName;
        x = x;
        y = y;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }
}
