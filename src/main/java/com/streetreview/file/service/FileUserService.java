package com.streetreview.file.service;

import com.streetreview.file.entity.PhotoDto;
import com.streetreview.file.entity.PhotoType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileUserService {
    List<PhotoDto> saveFile(List<MultipartFile> files, PhotoType photoType, String targetId) throws IOException;
    void deleteFile(PhotoType photoType, String targetId);

}
