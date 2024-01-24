package com.streetreview.review.service;


import com.streetreview.member.entity.Member;
import com.streetreview.member.handler.CustomException;
import com.streetreview.member.handler.StatusCode;
import com.streetreview.member.repository.MemberRepository;
import com.streetreview.review.dto.ReviewDto;
import com.streetreview.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;

    @Override
    public void writeReview(ReviewDto reviewDto) {

        //회원 검색
        memberRepository.findById(reviewDto.getMemberId())
                .orElseThrow(() -> new CustomException(StatusCode.NOT_FOUND_MEMBER));


        reviewRepository.save(reviewDto.toReviewEntity());
    }
}


