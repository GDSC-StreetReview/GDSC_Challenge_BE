package com.streetreview.review.service;


import com.streetreview.member.entity.Member;
import com.streetreview.member.repository.MemberRepository;
import com.streetreview.review.dto.ReviewDto;
import com.streetreview.review.entity.Review;
import com.streetreview.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;


@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;

    @Override
    public void writeReview(ReviewDto reviewDto) {

        //회원 검색
        Member member = memberRepository.findById(reviewDto.getMemberId())
                .orElseThrow(() -> new EntityNotFoundException("회원이 존재하지 않습니다."));


        // 리뷰 엔티티 생성
        Review review = Review.builder()
                .content(reviewDto.getContent())
                .photo(reviewDto.getPhoto())
                .member(member)
                .build();

        // 리뷰 저장
        reviewRepository.save(review);
    }
}


