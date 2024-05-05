package com.streetreview.review.dto;

import com.streetreview.review.entity.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReqWriteReviewDto {

    private String content;
    private String photo;
    private Double myX;
    private Double myY;
    //길거리 좌표
    private Double reviewX;
    private Double reviewY;

    public ReqWriteReviewDto(String content, String photo, Double myX, Double myY, Double reviewX, Double reviewY) {
        this.content = content;
        this.photo = photo;
        this.myX = myX;
        this.myY = myY;
        this.reviewX = reviewX;
        this.reviewY = reviewY;
    }

    @Builder
    public Review toReviewEntity() {
        return Review.builder()
                .content(content)
                .photo(photo)
                .x(reviewX)
                .y(reviewY)
                .build();
    }
}
