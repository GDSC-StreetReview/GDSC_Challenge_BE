package com.streetreview.review.entity;

import com.streetreview.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class ReviewLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "liked", nullable = false)
    private boolean liked;

    @Builder
    public ReviewLike(Review review, Member member, boolean liked) {
        this.review = review;
        this.member = member;
        this.liked = liked;
    }

    public static ReviewLike createReviewLike(Review review, Member member, boolean liked) {
        return ReviewLike.builder()
                .review(review)
                .member(member)
                .liked(liked)
                .build();
    }
}
