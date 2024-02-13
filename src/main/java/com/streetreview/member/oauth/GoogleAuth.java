package com.streetreview.member.oauth;

import com.streetreview.member.dto.GoogleAuthDto;
import com.streetreview.member.dto.ResGoogleToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
public class GoogleAuth {
    @Value("${oauth2.google.client-id}")
    private String clientId;
    @Value("${oauth2.google.client-secret}")
    private String clientSecret;

    public static final String GOOGLE = "google";
    @Value("${google.redirect}")
    public String REDIRECT_URI;

    public static final String TOKEN_URL = "https://oauth2.googleapis.com/token";
    public static final String MEMBER_INFO_URL = "https://oauth2.googleapis.com/tokeninfo";

    public ResGoogleToken getGoogleOauthToken(String code) {
        String decode = URLDecoder.decode(code, StandardCharsets.UTF_8);
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        System.out.println("redirect-uri = " + REDIRECT_URI);
        params.add("code", decode);
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", REDIRECT_URI);
        params.add("grant_type", "authorization_code");

        return restTemplate.postForObject(TOKEN_URL, params, ResGoogleToken.class);
    }

    public GoogleAuthDto getGoogleOauthTokenInfo(String id_token) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> token = Map.of("id_token", id_token);
        GoogleAuthDto googleAuthDto = restTemplate.postForEntity(MEMBER_INFO_URL, token, GoogleAuthDto.class).getBody();
        return googleAuthDto;
    }
}
