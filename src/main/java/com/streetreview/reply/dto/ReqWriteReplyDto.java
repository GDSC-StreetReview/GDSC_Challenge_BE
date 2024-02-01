package com.streetreview.reply.dto;

import com.streetreview.reply.entity.Reply;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReqWriteReplyDto {

    private Long reviewId;
    private String replyContent;
    private String replyPhoto;
    private Double myX;
    private Double myY;

    @Builder
    public ReqWriteReplyDto(Long reviewId, String replyContent, String replyPhoto) {
        this.reviewId = reviewId;
        this.replyContent = replyContent;
        this.replyPhoto = replyPhoto;
    }


    public Reply toReplyEntity() {
        return Reply.builder()
                .replyContent(replyContent)
                .replyPhoto(replyPhoto)
                .replyLikey(0)
                .build();
    }
}
