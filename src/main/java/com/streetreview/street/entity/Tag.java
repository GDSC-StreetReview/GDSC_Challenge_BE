package com.streetreview.street.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class Tag {
    private String value;

    public Tag(String value) {
        this.value = value;
    }
}
