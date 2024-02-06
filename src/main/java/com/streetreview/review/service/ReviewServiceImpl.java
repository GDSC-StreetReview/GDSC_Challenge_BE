package com.streetreview.review.service;


import com.streetreview.common.dto.Message;
import com.streetreview.file.entity.Photo;
import com.streetreview.file.entity.PhotoType;
import com.streetreview.file.repository.PhotoRepository;
import com.streetreview.member.entity.Member;
import com.streetreview.member.handler.CustomException;
import com.streetreview.member.handler.StatusCode;
import com.streetreview.member.repository.MemberRepository;
import com.streetreview.review.dto.*;
import com.streetreview.review.entity.Review;
import com.streetreview.review.repository.ReviewRepository;
import com.streetreview.street.repository.StreetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final StreetRepository streetRepository;
    private final PhotoRepository photoRepository;

    @Override
    @Transactional
    public ResReviewIdDto writeReview(ReqWriteReviewDto reqWriteReviewDto, Long memberId) {

        //회원 검색
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new CustomException(StatusCode.NOT_FOUND));

        Review review = reqWriteReviewDto.toReviewEntity();
        member.addReview(review);

        return new ResReviewIdDto(reviewRepository.save(review).getReviewId());
    }

    @Override
    public List<ResReviewListDto> viewReviewList(ReqStreetPointDto reqStreetPointDto) {
        streetRepository.findByLocation(new GeoJsonPoint(reqStreetPointDto.getY(), reqStreetPointDto.getX()))
                .orElseThrow(() -> new CustomException(StatusCode.NOT_FOUND));

        return reviewRepository.findByXAndYOrderByCreatedDateDesc(reqStreetPointDto.getX(), reqStreetPointDto.getY())
                .stream().map(review -> {
                    List<String> photoUrlList = photoRepository.findByTargetIdAndType(String.valueOf(review.getReviewId()), PhotoType.REVIEW.getValue())
                            .stream().map(Photo::getFileUrl).collect(Collectors.toList());
                    return review.toResReviewListDto(review.getMember().toMemberProfileDto(), photoUrlList);
                }).collect(Collectors.toList());

        /*
            List<ResReviewListDto> reviewListDtoList = new ArrayList<>();
            for(int i = 0; i < reviewList.size(); i++) {
                Review review = reviewList.get(i);
                Member member = review.getMember();
                reviewListDtoList.add(review.toResReviewListDto(member));
            }
         */

    }

    @Override
    @Transactional
    public void deleteReview(Long reviewId, Long memberId){
        Review review = reviewRepository.findByReviewIdAndMember_memberId(reviewId,memberId)
                        .orElseThrow(() -> new CustomException(StatusCode.NOT_FOUND));
        review.setContent("삭제된 리뷰입니다.");

        reviewRepository.save(review);
    }


}


