package com.streetreview.member.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "nickname", unique = true)
    private String nickName;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    @Column(name = "account_status")
    private String accountStatus;

    @Builder
    public Member(String email, String nickName, String password, String role, String accountStatus) {
        this.email = email;
        this.nickName = nickName;
        this.password = password;
        this.role = role;
        this.accountStatus = accountStatus;
    }


}
