package com.streetreview.file.controller;

import com.streetreview.common.dto.Message;
import com.streetreview.file.entity.PhotoType;
import com.streetreview.file.service.FileService;
import com.streetreview.file.service.FileServiceImpl;
import com.streetreview.file.service.FileUserService;
import com.streetreview.file.service.FileUserServiceImpl;
import com.streetreview.member.handler.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class FileController {
    private final FileService fileService;
    private final FileUserService fileUserService;

    @Autowired
    public FileController(FileServiceImpl fileService, FileUserServiceImpl fileUserService) {
        this.fileService = fileService;
        this.fileUserService = fileUserService;
    }


    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(HttpServletRequest request, @PathVariable String fileName) {
        return fileService.loadFileAsResource(request, fileName);
    }

    @GetMapping("/image")
    public ResponseEntity<Resource> getImage(@RequestParam Map<String, String> param) {
        return fileService.showImage(param);
    }

    @PostMapping("/upload/{photoType}/{targetId}")
    public ResponseEntity<Message> uploadImage(@RequestPart(value = "image", required = false) List<MultipartFile> files, @PathVariable String photoType, @PathVariable String targetId) throws IOException {
        fileUserService.saveFile(files, PhotoType.fromString(photoType), targetId);
        return ResponseEntity.ok(new Message(StatusCode.OK));
    }


}
