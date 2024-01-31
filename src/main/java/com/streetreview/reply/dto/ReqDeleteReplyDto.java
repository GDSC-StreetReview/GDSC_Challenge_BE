package com.streetreview.reply.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReqDeleteReplyDto {

    private Long replyId;
    private Long memberId;

    @Builder
    public ReqDeleteReplyDto(Long replyId, Long memberId){
        this.replyId = replyId;
        this.memberId = memberId;
    }
}
