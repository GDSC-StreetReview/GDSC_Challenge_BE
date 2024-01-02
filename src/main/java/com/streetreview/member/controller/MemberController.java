package com.streetreview.member.controller;

import com.streetreview.member.dto.ResGoogleToken;
import com.streetreview.member.oauth.GoogleAuth;
import com.streetreview.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;
    private final GoogleAuth googleAuth;

    @GetMapping("/oauth2/google")
    public ResponseEntity<ResGoogleToken> OAuthGoogleLogin(@RequestHeader("code") String code) {
        ResGoogleToken resGoogleToken = googleAuth.getGoogleOauthToken(code);

        googleAuth.getGoogleOauthTokenInfo(resGoogleToken.getId_token());
        return ResponseEntity.ok(resGoogleToken);
    }
}
