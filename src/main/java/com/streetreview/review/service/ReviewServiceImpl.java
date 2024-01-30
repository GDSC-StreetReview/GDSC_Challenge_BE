package com.streetreview.review.service;


import com.streetreview.file.entity.Photo;
import com.streetreview.file.entity.PhotoType;
import com.streetreview.file.repository.PhotoRepository;
import com.streetreview.member.entity.Member;
import com.streetreview.member.handler.CustomException;
import com.streetreview.member.handler.StatusCode;
import com.streetreview.member.repository.MemberRepository;
import com.streetreview.review.dto.ReqStreetPointDto;
import com.streetreview.review.dto.ResReviewIdDto;
import com.streetreview.review.dto.ResReviewListDto;
import com.streetreview.review.dto.ReqWriteReviewDto;
import com.streetreview.review.entity.Review;
import com.streetreview.review.repository.ReviewRepository;
import com.streetreview.street.repository.StreetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
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
        System.out.println("@@ : " + reqStreetPointDto.getX());

        return reviewRepository.findByXAndYOrderByCreatedDateDesc(reqStreetPointDto.getX(), reqStreetPointDto.getY())
                .stream().map(review -> {
                    List<String> photoUrlList = photoRepository.findByTargetIdAndType(String.valueOf(review.getReviewId()), PhotoType.REVIEW.getValue())
                            .stream().map(Photo::getFileUrl).collect(Collectors.toList());
                    return review.toResReviewListDto(review.getMember(), photoUrlList);
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


}


