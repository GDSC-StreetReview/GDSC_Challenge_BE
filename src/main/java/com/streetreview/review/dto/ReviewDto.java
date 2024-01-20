package com.streetreview.review.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewDto {

    private String content;
    private String photo;
    private Long streetId;
    private Long memberId;

    @Builder
    public ReviewDto(String content, String photo,Long streetId, Long memberId) {
        this.content = content;
        this.photo = photo;
        this.streetId = streetId;
        this.memberId = memberId;
    }
}
