package com.streetreview.member.security;

import com.streetreview.member.repository.MemberRepository;
import com.streetreview.member.security.dto.ClaimName;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class JwtInfoExtractor {

    @Value("${jwt.access.key}")
    private String accessKey;

    public Claims extractToken(String token) {
        return Jwts.parser().setSigningKey(accessKey.getBytes()).parseClaimsJws(token).getBody();
    }

    public static Long getStrvMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken) {
            Claims claims = ((JwtAuthenticationToken) authentication).getClaims();
            claims.get(ClaimName.ID.getValue());
            return Long.parseLong(claims.get(ClaimName.ID.getValue()).toString());
        }

        return null;
    }

    public static String getStrvRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken) {
            Claims claims = ((JwtAuthenticationToken) authentication).getClaims();
            claims.get(ClaimName.ROLE.getValue());
            return claims.get(ClaimName.ROLE.getValue()).toString();
        }

        return null;
    }
}
