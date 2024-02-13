package com.streetreview.reply.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReqReportReplyDto {

    private Long replyId;

    public ReqReportReplyDto(Long replyId) {
        this.replyId = replyId;
    }
}
