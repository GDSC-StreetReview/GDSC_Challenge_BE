package com.streetreview.mypage.service;

import com.streetreview.member.dto.MemberProfileDto;
import com.streetreview.member.entity.Member;
import com.streetreview.member.handler.CustomException;
import com.streetreview.member.handler.StatusCode;
import com.streetreview.member.repository.MemberRepository;
import com.streetreview.reply.dto.ResReplyListDto;
import com.streetreview.reply.entity.Reply;
import com.streetreview.reply.repository.ReplyRepository;
import com.streetreview.review.dto.ResReviewListDto;
import com.streetreview.review.entity.Review;
import com.streetreview.review.repository.ReviewRepository;
import com.streetreview.review.service.ReviewServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MyPageServiceImpl implements MyPageService {

    private final ReviewRepository reviewRepository;
    private final ReplyRepository replyRepository;
    private final MemberRepository memberRepository;

    @Override
    public List<ResReviewListDto> getMyReview(Long memberId) {
        List<Review> reviews = reviewRepository.findByMember_MemberId(memberId);
        return reviews.stream()
                .filter(review -> !review.getContent().equals(ReviewServiceImpl.DELETED))
                .map(Review::toResReviewListDto).collect(Collectors.toList());
    }

    @Override
    public ResReplyListDto getMyReply(Long replyId) {
        Reply reply = replyRepository.findByReplyId(replyId)
                .orElseThrow(() -> new CustomException(StatusCode.NOT_FOUND));

        return reply.toResReplyListDto();
    }


    public MemberProfileDto getMemberProfile(Long memberId) {
        // memberId를 사용해 회원 정보 호출
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new CustomException(StatusCode.NOT_FOUND));

        return MemberProfileDto.memberProfileDto(member);

    }
}
