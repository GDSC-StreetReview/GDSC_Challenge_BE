package com.streetreview.review.service;


import com.streetreview.member.entity.Member;
import com.streetreview.member.handler.CustomException;
import com.streetreview.member.handler.StatusCode;
import com.streetreview.member.repository.MemberRepository;
import com.streetreview.review.dto.ReqStreetPointDto;
import com.streetreview.review.dto.ResReviewListDto;
import com.streetreview.review.dto.ReviewDto;
import com.streetreview.review.entity.Review;
import com.streetreview.review.repository.ReviewRepository;
import com.streetreview.street.repository.StreetRepository;
import lombok.RequiredArgsConstructor;
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

    @Override
    @Transactional
    public void writeReview(ReviewDto reviewDto) {

        //회원 검색
        Member member = memberRepository.findById(reviewDto.getMemberId())
                .orElseThrow(() -> new CustomException(StatusCode.NOT_FOUND));

        Review review = reviewDto.toReviewEntity();

        review.writeBy(member);
        reviewRepository.save(reviewDto.toReviewEntity());
    }

    @Override
    public List<ResReviewListDto> viewReviewList(ReqStreetPointDto reqStreetPointDto) {
        streetRepository.findByXAndY(reqStreetPointDto.getX(), reqStreetPointDto.getY())
                .orElseThrow(() -> new CustomException(StatusCode.NOT_FOUND));

        return reviewRepository.findByXAndYOrderByCreatedDateDesc(reqStreetPointDto.getX(), reqStreetPointDto.getY())
                .stream().map(review -> review.toResReviewListDto()).collect(Collectors.toList());

        /*
            List<ResReviewListDto> reviewListDtoList = new ArrayList<>();
            for(int i = 0; i < reviewList.size(); i++) {
                Review review = reviewList.get(i);
                reviewListDtoList.add(review.toResReviewListDto());
            }
         */

    }


}


