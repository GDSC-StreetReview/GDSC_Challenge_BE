package com.streetreview.review.dto;


import com.streetreview.member.dto.MemberProfileDto;
import com.streetreview.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Getter
@NoArgsConstructor
public class ResReviewListDto {
    private Long reviewId;
    private Double reviewX;
    private Double reviewY;
    private String content;
    private int likey;
    private Timestamp createdDate;
    private Timestamp updatedDate;
    private MemberProfileDto member;
    private List<String> photoList;



    @Builder
    public ResReviewListDto(String content, int likey, Timestamp createdDate, Timestamp updatedDate, MemberProfileDto member,
                            List<String> photoList, Long reviewId, Double reviewX, Double reviewY) {
        this.content = content;
        this.reviewX = reviewX;
        this.reviewY = reviewY;
        this.likey = likey;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.member = member;
        this.photoList = photoList;
        this.reviewId = reviewId;
    }
}
