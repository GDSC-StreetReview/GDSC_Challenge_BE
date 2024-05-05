package com.streetreview.mypage.service;

import com.streetreview.member.dto.MemberProfileDto;
import com.streetreview.reply.dto.ResReplyListDto;
import com.streetreview.review.dto.ResReviewListDto;

import java.util.List;
import java.util.Optional;

public interface MyPageService {
    List<ResReviewListDto> getMyReview(Long memberId);

    ResReplyListDto getMyReply(Long ReplyId);

    MemberProfileDto getMemberProfile(Long memberId);
}
