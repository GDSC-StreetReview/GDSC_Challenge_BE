package com.streetreview.member.entity;

import com.streetreview.member.dto.Role;
import com.streetreview.member.handler.CustomException;
import com.streetreview.member.handler.StatusCode;
import com.streetreview.member.security.dto.AccountStatus;
import com.streetreview.member.security.dto.Token;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

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




}
