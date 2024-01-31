package com.streetreview.reply.service;

import com.streetreview.reply.dto.ReqDeleteReplyDto;
import com.streetreview.reply.dto.ReqWriteReplyDto;
import com.streetreview.review.dto.ResReviewIdDto;

public interface ReplyService {
    ResReviewIdDto writeReply(ReqWriteReplyDto reqWriteReplyDto, Long memberId);

    void deleteReply(ReqDeleteReplyDto reqDeleteReplyDto, Long memberId);
}
