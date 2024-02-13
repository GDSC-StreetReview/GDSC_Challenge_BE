package com.streetreview.review.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReqReportReviewDto {

    private Long reviewId;

    public ReqReportReviewDto(Long reviewId) {
        this.reviewId = reviewId;
    }
}
