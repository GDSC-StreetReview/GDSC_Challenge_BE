package com.streetreview.reply.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReqWriteDto {

    private String reply_content;
    private String reply_photo;
    private Long member_id;
    private Long review_id;

    @Builder
    public ReviewDto(String reply_content, String reply_photo, Long member_id, Long review_id) {
        this.reply_content = reply_content;
        this.reply_photo = reply_photo;
        this.review_id = review_id;
        this.
    }
}
