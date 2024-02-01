package com.streetreview.reply.service;

import com.streetreview.member.entity.Member;
import com.streetreview.member.handler.CustomException;
import com.streetreview.member.handler.StatusCode;
import com.streetreview.member.repository.MemberRepository;
import com.streetreview.reply.dto.ReqDeleteReplyDto;
import com.streetreview.reply.dto.ReqWriteReplyDto;
import com.streetreview.reply.dto.ResReplyIdDto;
import com.streetreview.reply.entity.Reply;
import com.streetreview.reply.repository.ReplyRepository;
import com.streetreview.review.dto.ResReviewIdDto;
import com.streetreview.review.entity.Review;
import com.streetreview.review.repository.ReviewRepository;
import com.streetreview.street.repository.StreetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@RequiredArgsConstructor
@Service
public class ReplyServiceImpl implements ReplyService {
    private final ReviewRepository reviewRepository;
    private final ReplyRepository replyRepository;
    private final MemberRepository memberRepository;
    private final StreetRepository streetRepository;
    private static final Double maxDistance = 5000.0; //10km

    @Override
    @Transactional
    public ResReplyIdDto writeReply(ReqWriteReplyDto reqWriteReplyDto, Long memberId) {
        streetRepository.findNearAndExact(reqWriteReplyDto.getMyY(), reqWriteReplyDto.getMyX(), maxDistance, reqWriteReplyDto.getReviewY(), reqWriteReplyDto.getReviewX())
                .orElseThrow(() -> new CustomException(StatusCode.NOT_LOCATION));
        //위치에 있다면 댓글 작성.
        //멤버가 있는지 먼저 확인하기
        Member writer = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new CustomException(StatusCode.FORBIDDEN));

        //리뷰가 있는지 먼저 확인하기
        Review review = reviewRepository.findByReviewId(reqWriteReplyDto.getReviewId())
                .orElseThrow(() -> new CustomException(StatusCode.NOT_FOUND));

        Reply reply = reqWriteReplyDto.toReplyEntity();

        review.addComment(reply);
        writer.addReply(reply);
        Long replyId = replyRepository.save(reply).getReplyId();
        return new ResReplyIdDto(replyId);
        //Review 는 데이터 베이스에 이미 존재
        //reply아직 미존재
        //그러니까 연관관게를 맺어줘야함

    }

    @Override
    @Transactional
    public void deleteReply(ReqDeleteReplyDto reqDeleteReplyDto, Long memberId) {
        //리뷰가 있는지 먼저 확인하기
        //댓글을 삭제하려는 사람이 작성자 본인인지 확인하기
        replyRepository.findByReplyId(reqDeleteReplyDto.getReplyId())
                .ifPresent(reply -> replyRepository.deleteByReplyIdAndMember_memberId(reply.getReplyId(), memberId));
    }
}