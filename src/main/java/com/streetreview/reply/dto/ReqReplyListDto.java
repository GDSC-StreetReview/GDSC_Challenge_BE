package com.streetreview.reply.dto;

import com.streetreview.review.entity.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReqReplyListDto {
    Long reviewId;

    public ReqReplyListDto(Long reviewId) {
        this.reviewId = reviewId;
    }
}
