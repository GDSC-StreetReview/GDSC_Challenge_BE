package com.streetreview.member.service;

import com.streetreview.member.security.dto.Token;

public interface MemberService {

    Token getOauthToken(String code);
}
