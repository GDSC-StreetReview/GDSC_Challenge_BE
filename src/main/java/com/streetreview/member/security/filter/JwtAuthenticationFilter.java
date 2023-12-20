package com.streetreview.member.security.filter;


import com.streetreview.member.security.JwtAuthenticationToken;
import com.streetreview.member.security.JwtInfoExtractor;
import com.streetreview.member.security.JwtValidator;
import com.streetreview.member.security.dto.AuthorizerDto;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    public static final int BEARER_TOKEN_SIZE = 7;
    public static final String AUTHORIZATION = "Authorization";
    public static final String AUTH_METHOD = "Bearer ";

    private final JwtValidator jwtValidator;
    private final JwtInfoExtractor jwtInfoExtractor;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String accessToken = getTokenFromRequest((HttpServletRequest) request);

        if(isTokenValid(request, accessToken)) {
            setAuthentication(accessToken, request);
        }

        chain.doFilter(request, response);
    }

    private boolean isTokenValid(ServletRequest request, String accessToken) {
        return accessToken != null && jwtValidator.validateToken(request, accessToken);
    }

    private void setAuthentication(String accessToken, ServletRequest request) {
        Claims claims = jwtInfoExtractor.extractToken(accessToken);
        UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(claims, request);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);


    }

    private JwtAuthenticationToken getAuthenticationToken(Claims claims, ServletRequest request) {
        String username = claims.getSubject();
        String role = claims.get(AuthorizerDto.ClaimName.ROLE.getValue()).toString();
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role));
        JwtAuthenticationToken authenticationToken = new JwtAuthenticationToken(username, null, authorities, claims);
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails((HttpServletRequest) request));
        return authenticationToken;
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(AUTH_METHOD)) {
            return bearerToken.substring(BEARER_TOKEN_SIZE);
        }
        return null;
    }
}