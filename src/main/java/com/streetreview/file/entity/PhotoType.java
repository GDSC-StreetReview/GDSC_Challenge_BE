package com.streetreview.file.entity;

import com.streetreview.member.handler.CustomException;
import com.streetreview.member.handler.StatusCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.EnumType;

@AllArgsConstructor
@Getter
public enum PhotoType {

    STREET("STREET", "/STREET"),
    REVIEW("REVIEW", "/REVIEW"),
    REPLY("REPLY", "/REPLY");

    private String value;
    private String path;

    public static PhotoType fromString(String value) {
        for (PhotoType type : PhotoType.values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new CustomException(StatusCode.NOT_FOUND);
    }

}
