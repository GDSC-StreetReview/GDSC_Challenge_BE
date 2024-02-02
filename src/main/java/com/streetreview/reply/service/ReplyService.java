package com.streetreview.reply.service;

import com.streetreview.reply.dto.ReqDeleteReplyDto;
import com.streetreview.reply.dto.ReqWriteReplyDto;
import com.streetreview.reply.dto.ResReplyIdDto;
import com.streetreview.reply.dto.ResReplyListDto;
import com.streetreview.review.dto.ResReviewIdDto;
import com.streetreview.review.entity.Review;

import java.util.List;

public interface ReplyService {
    ResReplyIdDto writeReply(ReqWriteReplyDto reqWriteReplyDto, Long memberId);

    void deleteReply(ReqDeleteReplyDto reqDeleteReplyDto, Long memberId);

    List<ResReplyListDto> getAllReplyList(Long review);
}
