package com.streetreview.member.controller;

import com.streetreview.common.dto.Message;
import com.streetreview.member.handler.StatusCode;
import com.streetreview.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.streetreview.member.security.JwtInfoExtractor.getStrvMember;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;
    @PostMapping("/oauth2/google")
    public ResponseEntity<Message> OAuthGoogleLogin(@RequestHeader("code") String code) {
        return ResponseEntity.ok(new Message(StatusCode.OK, memberService.getOauthToken(code)));
    }

    @GetMapping("/m/profile")
    public ResponseEntity<Message> myProfile() {
        return ResponseEntity.ok(new Message(StatusCode.OK, memberService.getMemberProfile(getStrvMember())));
    }

    @GetMapping("/test")
    public ResponseEntity<Message> test() {
        return ResponseEntity.ok(new Message(StatusCode.OK));
    }
}
