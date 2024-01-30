package com.streetreview.file.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "photo")
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "photo_id", unique = true, nullable = false)
    private Long photoId;

    @Column(name = "target_id")
    private String targetId;

    @Column
    private String type;

    @Column(name = "uu_id")
    private String uuId;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "file_url")
    private String fileUrl;

    @Column(name = "file_download_path")
    private String fileDownloadPath;

    @Column(name = "file_size")
    private Long fileSize;

    @Builder
    public Photo(String targetId, String type, String uuId, String fileName, String filePath, String fileUrl, String fileDownloadPath, Long fileSize) {
        this.targetId = targetId;
        this.type = type;
        this.uuId = uuId;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileUrl = fileUrl;
        this.fileDownloadPath = fileDownloadPath;
        this.fileSize = fileSize;
    }

    public PhotoDto toDto() {
        return PhotoDto.builder()
                .targetId(targetId)
                .type(type)
                .fileName(fileName)
                .fileUrl(fileUrl)
                .fileDownloadPath(fileDownloadPath)
                .fileSize(fileSize).build();
    }

}
