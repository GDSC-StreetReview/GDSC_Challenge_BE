package com.streetreview.file.service;

import com.streetreview.file.entity.Photo;
import com.streetreview.file.exception.FileDownloadException;
import com.streetreview.file.exception.FileUploadProperties;
import com.streetreview.file.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;

@Service
public class FileServiceImpl implements FileService{
    private final PhotoRepository photoRepository;

    @Autowired
    public FileServiceImpl(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    @Override
    public ResponseEntity<Resource> loadFileAsResource(HttpServletRequest request, String fileName) {
        try {
            Photo File = getFile(fileName);
            Resource resource = new UrlResource(File.getFileUrl());

            if(resource.exists()) {
                String contentType = getContentType(request, resource);

                if(contentType == null)
                    contentType = "application/octet-stream";

                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + File.getFileName() + "\"")
                        .body(resource);
            }
        } catch (MalformedURLException e) {
            throw new FileDownloadException(fileName + "File Not Found");
        }
        return null;
    }

    @Override
    public ResponseEntity<Resource> showImage(Map<String, String> param) {
        Optional<Photo> coPhotoOfProject = photoRepository.findByUuId(param.get("name"));
        if(coPhotoOfProject.isPresent()) {
            String fileUrl = coPhotoOfProject.get().getFilePath().replace("\\", "/");

            Resource resource = new FileSystemResource(fileUrl);
            if(!resource.exists())
                return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);

            HttpHeaders httpHeaders = new HttpHeaders();
            Path filePath = null;
            try {
                filePath = Paths.get(fileUrl);
                httpHeaders.add("Content-Type", Files.probeContentType(filePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new ResponseEntity<Resource>(resource, httpHeaders, HttpStatus.OK);
        }

        return null;
    }

    private Photo getFile(String uuId) {
        Optional<Photo> photo = photoRepository.findByUuId(uuId);

        return photo.orElse(null);
    }

    private String getContentType(HttpServletRequest request, Resource resource) {
        try {
            return request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
