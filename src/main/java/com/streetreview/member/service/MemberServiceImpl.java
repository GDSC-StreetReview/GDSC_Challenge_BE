package com.streetreview.member.service;

import com.streetreview.member.dto.GoogleAuthDto;
import com.streetreview.member.dto.MemberProfileDto;
import com.streetreview.member.dto.ResGoogleToken;
import com.streetreview.member.dto.Role;
import com.streetreview.member.entity.Member;
import com.streetreview.member.handler.CustomException;
import com.streetreview.member.handler.StatusCode;
import com.streetreview.member.oauth.GoogleAuth;
import com.streetreview.member.repository.MemberRepository;
import com.streetreview.member.security.JwtCreator;
import com.streetreview.member.security.dto.AccountStatus;
import com.streetreview.member.security.dto.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    private final GoogleAuth googleAuth;
    private final PasswordEncoder passwordEncoder;
    private final JwtCreator jwtCreator;

    @Override
    @Transactional
    public Token getOauthToken(String code) {
        try {
            ResGoogleToken resGoogleToken = googleAuth.getGoogleOauthToken(code);

            GoogleAuthDto googleAuthDto = googleAuth.getGoogleOauthTokenInfo(resGoogleToken.getId_token());

            Optional<Token> token = memberRepository.findByEmail(googleAuthDto.getEmail()).map(member -> verifyOauthAccount(member.getEmail()));

            return token.orElseGet(() -> signup(googleAuthDto));
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(StatusCode.INVALID_DATA_FORMAT);
        }
    }

    @Override
    public MemberProfileDto getMemberProfile(Long memberId) {
        Optional<MemberProfileDto> memberProfileDto = memberRepository.findByMemberId(memberId).map(Member::toMemberProfileDto);

        if(memberProfileDto.isEmpty())
            throw new CustomException(StatusCode.FORBIDDEN);

        return memberProfileDto.get();
    }

    private Token signup(GoogleAuthDto googleAuthDto) {
        Member newMember = memberRepository.save(Member.builder()
                .provider(GoogleAuth.GOOGLE)
                .providerId(GoogleAuth.GOOGLE + "_" + googleAuthDto.getSub())
                .email(googleAuthDto.getEmail())
                .picture(googleAuthDto.getPicture())
                .nickName(googleAuthDto.getGiven_name())
                .password(passwordEncoder.encode(Member.OAUTH_PW))
                .role(Role.USER)
                .accountStatus(AccountStatus.ACTIVATE).build());
        return memberRepository.findByEmail(newMember.getEmail()).map(member -> verifyOauthAccount(member.getEmail())).get();
    }

    private Token verifyOauthAccount(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new CustomException(StatusCode.USERNAME_NOT_FOUND));

        if(!passwordEncoder.matches(Member.OAUTH_PW, member.getPassword()))
            throw new CustomException(StatusCode.REGISTERED_EMAIL);
        else if(member.getAccountStatus().equals(AccountStatus.DORMANT))
            throw new CustomException(StatusCode.DORMANT_ACCOUNT);
        else if(member.getAccountStatus().equals(AccountStatus.DISABLED))
            throw new CustomException(StatusCode.DISABLED_ACCOUNT);

        return jwtCreator.createToken(member);
    }

}
