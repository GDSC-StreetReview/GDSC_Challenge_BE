package com.streetreview.review.dto;


import com.streetreview.member.entity.Member;
import lombok.Builder;

import java.sql.Timestamp;

public class ResReviewListDto {
    private String content;
    private int likey;
    private Timestamp createdDate;
    private Timestamp updatedDate;
    private Member member;


    @Builder
    public ResReviewListDto(String content, int likey, Timestamp createdDate, Timestamp updatedDate, Member member) {
        this.content = content;
        this.likey = likey;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.member = member;
    }





}
