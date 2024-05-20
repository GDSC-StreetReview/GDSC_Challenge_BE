package com.streetreview.review.repository;

import com.streetreview.member.entity.Member;
import com.streetreview.review.entity.Review;
import com.streetreview.review.entity.ReviewLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewLikeRepository extends JpaRepository<ReviewLike, Long> {

    boolean existsByReviewAndMember(Review review, Member member);

    List<ReviewLike> findByMember_MemberId(Long memberId);
}
