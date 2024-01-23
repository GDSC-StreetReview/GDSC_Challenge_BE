package com.streetreview.review.controller;

import com.streetreview.common.dto.Message;
import com.streetreview.member.entity.Member;
import com.streetreview.member.handler.StatusCode;
import com.streetreview.member.repository.MemberRepository;
import com.streetreview.review.dto.ReviewDto;
import com.streetreview.review.entity.Review;
import com.streetreview.review.repository.ReviewRepository;
import com.streetreview.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;

    @PostMapping("/write") // 리뷰 작성
    public ResponseEntity<Message> writeReview(@RequestBody ReviewDto reviewDto) {
        reviewService.writeReview(reviewDto);
        return ResponseEntity.ok(new Message(StatusCode.OK));
    }

    @GetMapping("/all")// 전체 리뷰 보여주기
    public ResponseEntity<Message> getAllReviews() {
        List<Review> reviews = reviewRepository.findAll();
        return ResponseEntity.ok(new Message(StatusCode.OK, reviews));
    }

    @GetMapping("/userReview") // 특정 사용자의 리뷰 보여주기
    public ResponseEntity<Message> getUserReviews(@RequestParam Long memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);

        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            List<Review> userReviews = reviewRepository.findByMember(member);
            return ResponseEntity.ok(new Message(StatusCode.OK, userReviews));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
