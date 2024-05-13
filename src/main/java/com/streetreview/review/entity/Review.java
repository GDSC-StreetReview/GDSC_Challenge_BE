package com.streetreview.review.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.streetreview.member.dto.MemberProfileDto;
import com.streetreview.member.entity.Member;
import com.streetreview.reply.entity.Reply;
import com.streetreview.review.dto.ResReviewListDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @Setter
    @Column(columnDefinition = "text")
    private String content;

    private String photo;

    private int likey;

    @CreatedDate
    private Timestamp createdDate;

    @LastModifiedDate
    private Timestamp updatedDate;

    private Double x;

    private Double y;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(
            mappedBy = "review",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )
    @JsonManagedReference
    private List<Reply> replyList = new ArrayList<>();

    @Column(name = "report_count")
    private int reportCount = 0;

    @Builder
    public Review(Long reviewId, String content, String photo, int likey, Timestamp createdDate, Timestamp updatedDate, Double x, Double y, Member member) {
        this.reviewId = reviewId;
        this.content = content;
        this.photo = photo;
        this.likey = likey;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.x = x;
        this.y = y;
        this.member = member;
    }

    public void writeBy(Member member) {
        this.member = member;
    }


    public ResReviewListDto toResReviewListDto(MemberProfileDto member, List<String> photoList) {
        return ResReviewListDto.builder()
                .reviewId(reviewId)
                .content(content)
                .likey(likey)
                .createdDate(createdDate)
                .updatedDate(updatedDate)
                .member(member)
                .photoList(photoList)
                .build();
    }


    public void addComment(Reply reply) {
        this.replyList.add(reply);
        reply.addReview(this);
    }

    public void increaseReportCount(int count) {
        this.reportCount += count;
    }

    public ResReviewListDto toResReviewListDto() {
        return ResReviewListDto.builder()
                .content(content)
                .likey(likey)
                .createdDate(createdDate)
                .updatedDate(updatedDate)
                .build();
    }

    public void increaseLikey() {
        this.likey++;
    }
}
