package com.streetreview.file.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PhotoDto {
    private String targetId;
    private String type;
    private String fileName;
    private String fileUrl;
    private String fileDownloadPath;
    private Long fileSize;

    @Builder
    public PhotoDto(String targetId, String type, String fileName, String fileUrl, String fileDownloadPath, Long fileSize) {
        this.targetId = targetId;
        this.type = type;
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.fileDownloadPath = fileDownloadPath;
        this.fileSize = fileSize;
    }


}
