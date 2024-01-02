package com.streetreview.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResGoogleToken {
    private String access_token;
    private Long expires_in;
    private String scope;
    private String token_type;
    private String id_token;

    @Builder
    public ResGoogleToken(String access_token, Long expires_in, String scope, String token_type, String id_token) {
        this.access_token = access_token;
        this.expires_in = expires_in;
        this.scope = scope;
        this.token_type = token_type;
        this.id_token = id_token;
    }
}
