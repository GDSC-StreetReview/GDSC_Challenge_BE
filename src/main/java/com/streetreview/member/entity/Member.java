package com.streetreview.member.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.streetreview.member.dto.MemberProfileDto;
import com.streetreview.member.dto.Role;
import com.streetreview.member.security.dto.AccountStatus;
import com.streetreview.reply.entity.Reply;
import com.streetreview.review.entity.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "member")
public class Member {
    public static final String OAUTH_PW = "test";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "provider")
    private String provider;

    @Column(name = "provider_id")
    private String providerId;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "nickname")
    private String nickName;

    @Column(name = "picture")
    private String picture;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_status")
    private AccountStatus accountStatus;

    @OneToMany(
            mappedBy = "member",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )
    @JsonManagedReference
    private List<Review> reviewList = new ArrayList<>();

    @OneToMany(
            mappedBy = "member",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )
    @JsonManagedReference
    private List<Reply> replyList = new ArrayList<>();

    @Builder
    public Member(String provider, String providerId, String email, String nickName, String picture, String password, Role role, AccountStatus accountStatus) {
        this.provider = provider;
        this.providerId = providerId;
        this.email = email;
        this.nickName = nickName;
        this.picture = picture;
        this.password = password;
        this.role = role;
        this.accountStatus = accountStatus;
    }

    public void addReview(Review review) {
        this.reviewList.add(review);
        review.writeBy(this);
    }

    public void addReply(Reply reply) {
        this.replyList.add(reply);
        reply.writeBy(this);
    }

    //프로필 조회 DTO
    public MemberProfileDto toMemberProfileDto() {
        return MemberProfileDto.builder()
                .memberId(memberId)
                .providerId(providerId)
                .email(email)
                .nickName(nickName)
                .picture(picture).build();
    }


}
