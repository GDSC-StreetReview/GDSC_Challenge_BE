package com.streetreview.reply.service;

import com.streetreview.reply.dto.ReqWriteReplyDto;

public interface ReplyService {
    void writeReply(ReqWriteReplyDto reqWriteReplyDto, Long memberId);
}
