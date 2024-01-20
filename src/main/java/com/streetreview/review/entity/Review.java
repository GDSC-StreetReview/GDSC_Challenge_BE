package com.streetreview.review.entity;

import com.streetreview.member.entity.Member;
import com.streetreview.street.entity.Street;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @Column(columnDefinition = "text")
    private String content;

    private String photo;

    private int likey;

    @CreatedDate
    private Date createdDate;

    @LastModifiedDate
    private Date updatedDate;

    @ManyToOne
    @JoinColumn(name = "street_id")
    private Street street;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Review(Long reviewId, String content, String photo, int likey, Date createdDate, Date updatedDate, Street street, Member member) {
        this.reviewId = reviewId;
        this.content = content;
        this.photo = photo;
        this.likey = likey;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.street = street;
        this.member = member;
    }
}
