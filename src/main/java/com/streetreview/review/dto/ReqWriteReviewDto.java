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
    private Double x;
    private Double y;

    @Builder
    public ReqWriteReviewDto(String content, String photo, Double x, Double y) {
        this.content = content;
        this.photo = photo;
        this.x = x;
        this.y = y;
    }

    public Review toReviewEntity() {
        return Review.builder()
                .content(content)
                .photo(photo)
                .x(x)
                .y(y)
                .build();
    }
}
