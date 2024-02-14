package com.streetreview.member.controller;

import com.streetreview.common.dto.Message;
import com.streetreview.member.handler.CustomException;
import com.streetreview.member.handler.StatusCode;
import com.streetreview.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.streetreview.member.security.JwtInfoExtractor.getStrvMember;
import static com.streetreview.member.security.JwtInfoExtractor.getStrvRole;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;
    @PostMapping("/oauth2/google")
    public ResponseEntity<Message> OAuthGoogleLogin(@RequestBody Map<String, String> request) {
        return ResponseEntity.ok(new Message(StatusCode.OK, memberService.getOauthToken(request.get("code"))));
    }

    @GetMapping("/m/profile")
    public ResponseEntity<Message> myProfile() {
        return ResponseEntity.ok(new Message(StatusCode.OK, memberService.getMemberProfile(getStrvMember())));
    }

    @GetMapping("/m/manager")
    public ResponseEntity<Message> isManager() {
        if(!getStrvRole().equals("MANAGER")) throw new CustomException(StatusCode.FORBIDDEN);
        return ResponseEntity.ok(new Message(StatusCode.OK));
    }

    @GetMapping("/test")
    public ResponseEntity<Message> test() {
        return ResponseEntity.ok(new Message(StatusCode.OK));
    }
}
