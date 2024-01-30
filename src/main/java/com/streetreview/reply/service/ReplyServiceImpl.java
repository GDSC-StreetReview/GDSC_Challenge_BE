package com.streetreview.reply.service;

import com.streetreview.member.entity.Member;
import com.streetreview.member.handler.CustomException;
import com.streetreview.member.handler.StatusCode;
import com.streetreview.member.repository.MemberRepository;
import com.streetreview.reply.dto.ReqWriteReplyDto;
import com.streetreview.reply.entity.Reply;
import com.streetreview.reply.repository.ReplyRepository;
import com.streetreview.review.dto.ResReviewIdDto;
import com.streetreview.review.entity.Review;
import com.streetreview.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class ReplyServiceImpl implements ReplyService {
    private final ReviewRepository reviewRepository;
    private final ReplyRepository replyRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public ResReviewIdDto writeReply(ReqWriteReplyDto reqWriteReplyDto, Long memberId) {
        //멤버가 있는지 먼저 확인하기
        Member writer = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new CustomException(StatusCode.FORBIDDEN));

        //리뷰가 있는지 먼저 확인하기
        Review review = reviewRepository.findByReviewId(reqWriteReplyDto.getReviewId())
                .orElseThrow(() -> new CustomException(StatusCode.NOT_FOUND));

        Reply reply = reqWriteReplyDto.toReplyEntity();

        review.addComment(reply);
        writer.addReply(reply);
        return new ResReviewIdDto(replyRepository.save(reply).getReplyId());

        //Review 는 데이터 베이스에 이미 존재
        //reply아직 미존재
        //그러니까 연관관게를 맺어줘야함


    }




}
