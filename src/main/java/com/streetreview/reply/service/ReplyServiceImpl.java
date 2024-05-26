package com.streetreview.reply.service;

import com.streetreview.file.entity.Photo;
import com.streetreview.file.entity.PhotoType;
import com.streetreview.file.repository.PhotoRepository;
import com.streetreview.member.dto.MemberProfileDto;
import com.streetreview.member.entity.Member;
import com.streetreview.member.handler.CustomException;
import com.streetreview.member.handler.StatusCode;
import com.streetreview.member.repository.MemberRepository;
import com.streetreview.reply.dto.*;
import com.streetreview.reply.entity.Reply;
import com.streetreview.reply.repository.ReplyRepository;
import com.streetreview.review.dto.ResReviewIdDto;
import com.streetreview.review.entity.Review;
import com.streetreview.review.repository.ReviewRepository;
import com.streetreview.street.entity.Street;
import com.streetreview.street.repository.StreetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class ReplyServiceImpl implements ReplyService {
    private final ReviewRepository reviewRepository;
    private final ReplyRepository replyRepository;
    private final MemberRepository memberRepository;
    private final StreetRepository streetRepository;
    private final PhotoRepository photoRepository;
    private static final Double maxDistance = 5000.0; //5km

    @Override
    @Transactional
    public ResReplyIdDto writeReply(ReqWriteReplyDto reqWriteReplyDto, Long memberId) {
        streetRepository.findNear(reqWriteReplyDto.getMyY(), reqWriteReplyDto.getMyX(), maxDistance)
                .stream().filter(street -> street.isTarget(reqWriteReplyDto.getReviewX(), reqWriteReplyDto.getReviewY())).findFirst()
                //위치에 없다면 오류 출력
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

    @Override
    public List<ResReplyListDto> getAllReplyList(Long reviewId) {
        return replyRepository.findByReview_ReviewId(reviewId)
                .stream().map(reply -> {
                    MemberProfileDto memberProfileDto = reply.getMember().toMemberProfileDto();
                    List<String> photoUrlList = photoRepository.findByTargetIdAndType(String.valueOf(reply.getReplyId()), PhotoType.STREET.getValue())
                            .stream().map(Photo::getFileUrl).collect(Collectors.toList());
                    return reply.toResReplyListDto(memberProfileDto, photoUrlList);
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void reportReply(ReqReportReplyDto reqReportReplyDto, Long memberId) {

        // 댓글 찾아서 reportCount 증가
        replyRepository.findByReplyId(reqReportReplyDto.getReplyId())
                .map(reply -> {
                    reply.increaseReplyCount(1);
                    replyRepository.save(reply);

                    // 신고 횟수 5회 이상 => 삭제
                    if (reply.getReportCount() >= 5) {
                        replyRepository.delete(reply);
                    }
                    return reply;
                })
                .orElseThrow(() -> new CustomException(StatusCode.NOT_FOUND));
    }
}