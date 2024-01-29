package com.streetreview.review.repository;

import com.streetreview.member.entity.Member;
import com.streetreview.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<Review> findByReviewId(Long reviewId);
    List<Review> findByMember_MemberId(Long memberId);
    List<Review> findByMember(Member member);

    List<Review> findByXAndYOrderByCreatedDateDesc(Double x, Double Y);

    //select * from Review where x = ? and y = ? order by createdDate desc

}
