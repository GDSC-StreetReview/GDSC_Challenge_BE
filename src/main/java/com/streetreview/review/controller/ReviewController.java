package com.streetreview.review.controller;

import com.streetreview.common.dto.Message;
import com.streetreview.member.handler.StatusCode;
import com.streetreview.review.dto.ReqReportReviewDto;
import com.streetreview.review.dto.ReqStreetPointDto;
import com.streetreview.review.dto.ReqWriteReviewDto;
import com.streetreview.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.streetreview.member.security.JwtInfoExtractor.getStrvMember;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/write") // 리뷰 작성
    public ResponseEntity<Message> writeReview(@RequestBody ReqWriteReviewDto reqWriteReviewDto) {
        return ResponseEntity.ok(new Message(StatusCode.OK, reviewService.writeReview(reqWriteReviewDto, getStrvMember())));
    }


    @PostMapping("/all")// 전체 리뷰 보여주기
    public ResponseEntity<Message> getAllReviews(@RequestBody ReqStreetPointDto reqStreetPointDto) {
        return ResponseEntity.ok(new Message(StatusCode.OK, reviewService.viewReviewList(reqStreetPointDto)));
    }


    //1 - 주소?memberId=1 requestParam
    //2 - /reviews/1/2 pathvariable
    @GetMapping("/{memberId}") // 특정 사용자의 리뷰 보여주기
    public ResponseEntity<Message> getUserReviews(@PathVariable(name = "memberId") Long memberId) {

        return ResponseEntity.ok(new Message(StatusCode.OK));
    }

    @PatchMapping("/{reviewId}")
    public ResponseEntity<Message> modifyReviews(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId, getStrvMember());
        return ResponseEntity.ok(new Message(StatusCode.OK));
    }

    @PutMapping("/report")
    public ResponseEntity<Message> reportReview(@RequestBody ReqReportReviewDto reqReportReviewDto) {
        reviewService.reportReview(reqReportReviewDto, getStrvMember());
        return ResponseEntity.ok(new Message(StatusCode.OK));
    }
}
