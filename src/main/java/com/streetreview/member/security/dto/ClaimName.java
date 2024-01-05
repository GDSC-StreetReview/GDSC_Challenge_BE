package com.streetreview.member.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ClaimName {
    ID("ID"),
    ROLE("ROLE");
    private final String value;
}