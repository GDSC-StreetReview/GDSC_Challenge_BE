package com.streetreview.mypage.service;

import com.streetreview.file.entity.Photo;
import com.streetreview.file.entity.PhotoType;
import com.streetreview.file.repository.PhotoRepository;
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
import com.streetreview.review.entity.ReviewLike;
import com.streetreview.review.repository.ReviewLikeRepository;
import com.streetreview.review.repository.ReviewRepository;
import com.streetreview.review.service.ReviewServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MyPageServiceImpl implements MyPageService {

    private final ReviewRepository reviewRepository;
    private final ReplyRepository replyRepository;
    private final MemberRepository memberRepository;
    private final ReviewLikeRepository reviewLikeRepository;
    private final PhotoRepository photoRepository;


    @Override
    public List<ResReviewListDto> getMyReview(Long memberId) {
        List<Review> reviews = reviewRepository.findByMember_MemberId(memberId);
        return reviews.stream()
                .filter(review -> !review.getContent().equals(ReviewServiceImpl.DELETED))
                .map(review -> review.toResReviewListDto(
                        review.getMember().toMemberProfileDto(),
                        Collections.emptyList()  // 빈 리스트를 반환
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<ResReplyListDto> getMyReply(Long memberId) {
        memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new CustomException(StatusCode.NOT_FOUND));

        return replyRepository.findByMember_memberId(memberId)
                .stream().map(reply -> {
                    MemberProfileDto memberProfileDto = reply.getMember().toMemberProfileDto();
                    List<String> photoUrlList = photoRepository.findByTargetIdAndType(String.valueOf(reply.getReplyId()), PhotoType.REPLY.getValue())
                            .stream().map(Photo::getFileUrl).collect(Collectors.toList());
                    return reply.toResReplyListDto(memberProfileDto, photoUrlList);
                })
                .collect(Collectors.toList());
    }



    public MemberProfileDto getMemberProfile(Long memberId) {
        // memberId를 사용해 회원 정보 호출
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new CustomException(StatusCode.NOT_FOUND));

        return MemberProfileDto.memberProfileDto(member);
    }



    @Override
    public List<ResReviewListDto> getMyReviewLikes(Long memberId) {
        List<ReviewLike> reviewLikes = reviewLikeRepository.findByMember_MemberId(memberId);

        if (reviewLikes.isEmpty()) {
            throw new CustomException(StatusCode.NOT_FOUND);
        }

        return reviewLikes.stream()
                .map(ReviewLike::getReview)
                .map(review -> {
                    if (!review.getContent().equals(ReviewServiceImpl.DELETED)) {
                        MemberProfileDto memberDto = review.getMember().toMemberProfileDto();
                        List<String> photoUrlList = photoRepository.findByTargetIdAndType(String.valueOf(review.getReviewId()), PhotoType.REVIEW.getValue())
                                .stream().map(Photo::getFileUrl).collect(Collectors.toList());
                        return review.toResReviewListDto(memberDto, photoUrlList);
                    } else {
                        return null; // 삭제된 리뷰일 경우 null 반환
                    }
                })
                .filter(Objects::nonNull) // null이 아닌 요소만 필터링
                .collect(Collectors.toList());
    }
}