package com.streetreview.member.dto;

import com.streetreview.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberProfileDto {
    private Long memberId;
    private String providerId;
    private String email;
    private String nickName;
    private String picture;

    @Builder
    public MemberProfileDto(Long memberId, String providerId, String email, String nickName, String picture) {
        this.memberId = memberId;
        this.providerId = providerId;
        this.email = email;
        this.nickName = nickName;
        this.picture = picture;
    }

    public static MemberProfileDto memberProfileDto(Member member) {
        return MemberProfileDto.builder()
                .memberId(member.getMemberId())
                .email(member.getEmail())
                .nickName(member.getNickName())
                .picture(member.getPicture())
                .build();
    }
}
