package com.streetreview.review.service;

import com.streetreview.common.dto.Message;
import com.streetreview.review.dto.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface ReviewService {

    ResReviewIdDto writeReview(ReqWriteReviewDto reqWriteReviewDto, Long MemberId);
    List<ResReviewListDto> viewReviewList(ReqStreetPointDto reqStreetPointDto);
    void deleteReview(Long reviewId, Long memberId);
    void reportReview(ReqReportReviewDto reqReportReviewDto, Long memberId);
    List<ResReviewListDto> viewPagingReviewList(ReqStreetPointDto reqStreetPointDto, Pageable pageable);
}
