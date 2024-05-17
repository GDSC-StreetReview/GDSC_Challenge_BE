package com.streetreview.review.repository;

import com.streetreview.member.entity.Member;
import com.streetreview.review.dto.ResReviewListDto;
import com.streetreview.review.entity.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<Review> findByReviewId(Long reviewId);
    List<Review> findByMember_MemberId(Long memberId);
    List<Review> findByMember(Member member);

    List<Review> findByXAndYOrderByCreatedDateDesc(Double x, Double Y);

    @Query("SELECT COALESCE(COUNT(r), 0) FROM Review r WHERE r.x = :xValue AND r.y = :yValue GROUP BY r.x, r.y")
    Optional<Integer> groupAndCountByXAndY(@Param("xValue") Double xValue, @Param("yValue") Double yValue);


    //select * from Review where x = ? and y = ? order by createdDate desc

    Optional<Review> findByReviewIdAndMember_memberId(Long reviewId, Long memberId);

    List<Review> findAllByXAndYOrderByCreatedDateDesc(Double x, Double y, Pageable pageable);
}
