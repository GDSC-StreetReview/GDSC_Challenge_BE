package com.streetreview.review.service;

import com.streetreview.review.dto.ReqStreetPointDto;
import com.streetreview.review.dto.ResReviewListDto;
import com.streetreview.review.dto.ReviewDto;

import java.util.List;


public interface ReviewService {

    void writeReview(ReviewDto reviewDto);
    List<ResReviewListDto> viewReviewList(ReqStreetPointDto reqStreetPointDto);
}
