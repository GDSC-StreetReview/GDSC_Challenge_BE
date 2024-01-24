package com.streetreview.review.dto;


import lombok.Builder;

import java.util.Date;

public class ResReviewListDto {
    private String content;
    private int likey;
    private Date createdDate;
    private Date updatedDate;


    @Builder
    public ResReviewListDto(String content, int likey, Date createdDate, Date updatedDate, String temp) {
        this.content = content;
        this.likey = likey;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }





}
