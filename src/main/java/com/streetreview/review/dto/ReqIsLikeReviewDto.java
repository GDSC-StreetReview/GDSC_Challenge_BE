package com.streetreview.review.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReqIsLikeReviewDto {
    private Long reviewId;

    public ReqIsLikeReviewDto(Long reviewId) {
        this.reviewId = reviewId;
    }
}
