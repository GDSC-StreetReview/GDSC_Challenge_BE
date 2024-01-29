package com.streetreview.review.service;

import com.streetreview.review.dto.ReqStreetPointDto;
import com.streetreview.review.dto.ResReviewListDto;
import com.streetreview.review.dto.ReqWriteReviewDto;

import java.util.List;


public interface ReviewService {

    void writeReview(ReqWriteReviewDto reqWriteReviewDto, Long MemberId);
    List<ResReviewListDto> viewReviewList(ReqStreetPointDto reqStreetPointDto);
}
