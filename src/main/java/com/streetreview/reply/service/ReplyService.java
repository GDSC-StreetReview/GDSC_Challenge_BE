package com.streetreview.reply.service;

import com.streetreview.reply.dto.*;
import com.streetreview.review.dto.ResReviewIdDto;
import com.streetreview.review.entity.Review;

import java.util.List;

public interface ReplyService {
    ResReplyIdDto writeReply(ReqWriteReplyDto reqWriteReplyDto, Long memberId);

    void deleteReply(Long replyId, Long memberId);

    List<ResReplyListDto> getAllReplyList(Long review);

    void reportReply(ReqReportReplyDto reqReportReplyDto, Long memberId);
}

