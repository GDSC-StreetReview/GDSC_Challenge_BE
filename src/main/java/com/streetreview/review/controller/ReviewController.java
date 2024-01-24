package com.streetreview.review.controller;

import com.streetreview.common.dto.Message;
import com.streetreview.member.entity.Member;
import com.streetreview.member.handler.CustomException;
import com.streetreview.member.handler.StatusCode;
import com.streetreview.member.repository.MemberRepository;
import com.streetreview.review.dto.ReqStreetPointDto;
import com.streetreview.review.dto.ReviewDto;
import com.streetreview.review.entity.Review;
import com.streetreview.review.repository.ReviewRepository;
import com.streetreview.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Component
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/write") // 리뷰 작성
    public ResponseEntity<Message> writeReview(@RequestBody ReviewDto reviewDto) {
        reviewService.writeReview(reviewDto);
        return ResponseEntity.ok(new Message(StatusCode.OK));
    }



    @GetMapping("/all")// 전체 리뷰 보여주기
    public ResponseEntity<Message> getAllReviews(@RequestBody ReqStreetPointDto reqStreetPointDto) {
        return ResponseEntity.ok(new Message(StatusCode.OK, reviewService.viewReviewList(reqStreetPointDto)));
    }


    //1 - 주소?memberId=1 requestParam
    //2 - /reviews/1/2 pathvariable
    @GetMapping("/{memberId}") // 특정 사용자의 리뷰 보여주기
    public ResponseEntity<Message> getUserReviews(@PathVariable Long memberId) {

        return ResponseEntity.ok(new Message(StatusCode.OK));
    }
}
