package com.streetreview.file.service;

import com.fasterxml.jackson.core.io.IOContext;
import com.streetreview.file.entity.Photo;
import com.streetreview.file.entity.PhotoDto;
import com.streetreview.file.entity.PhotoType;
import com.streetreview.file.exception.FileUploadProperties;
import com.streetreview.file.repository.PhotoRepository;
import com.streetreview.member.handler.CustomException;
import com.streetreview.member.handler.StatusCode;
import com.streetreview.reply.repository.ReplyRepository;
import com.streetreview.review.repository.ReviewRepository;
import com.streetreview.street.entity.Street;
import com.streetreview.street.repository.StreetRepository;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FileUserServiceImpl implements FileUserService{
    private final PhotoRepository photoRepository;
    private final StreetRepository streetRepository;
    private final ReviewRepository reviewRepository;
    private final ReplyRepository replyRepository;

    private final Path fileLocation;
    public static final String URL_VIEW = "/image?name=";
    public static final String URL_DOWNLOAD = "/downloadFile/";

    @Autowired
    public FileUserServiceImpl(PhotoRepository photoRepository, FileUploadProperties fileUploadProperties, StreetRepository streetRepository, ReviewRepository reviewRepository, ReplyRepository replyRepository) {
        this.photoRepository = photoRepository;
        this.streetRepository = streetRepository;
        this.reviewRepository = reviewRepository;
        this.replyRepository = replyRepository;
        this.fileLocation = Paths.get(fileUploadProperties.getUploadDir()).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileLocation);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<PhotoDto> saveFile(List<MultipartFile> files, PhotoType photoType, String targetId) throws IOException {
        List<PhotoDto> photoDtoList = new ArrayList<>();

        deleteFile(photoType, targetId);

        for(MultipartFile file : files) {
            photoDtoList.add(uploadFile(file, photoType, targetId));
        }
        return photoDtoList;

    }

    @Override
    public void deleteFile(PhotoType photoType, String targetId) {
        List<Photo> photoList = photoRepository.findByTargetIdAndType(targetId, photoType.getValue());
        if(photoList != null) {
            for(Photo list : photoList) {
                File listOfFile = new File(list.getFilePath());
                if(listOfFile.exists()) {
                    listOfFile.delete();
                    photoRepository.deleteByPhotoId(list.getPhotoId());
                }

            }
        }
    }

    private PhotoDto uploadFile(MultipartFile file, PhotoType photoType, String targetId) throws IOException {
        String originFileName = file.getOriginalFilename();
        String fileName = StringUtils.cleanPath(parseUUID(originFileName));

        if(fileName.contains(".."))
            throw new FileUploadException("File Name is Not Visible");
        Path targetLocation = Path.of(this.fileLocation.toString(), photoType.getPath(), fileName);
        Files.createDirectories(targetLocation.getParent());
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        File FILE = new File(targetLocation.toString());
        long bytes = FILE.length() / 1024;

        Photo photo = Photo.builder()
                .targetId(targetId)
                .type(photoType.getValue())
                .uuId(FILE.getName())
                .fileName(originFileName)
                .filePath(targetLocation.toString())
                .fileUrl(parseFileUrl(URL_VIEW, FILE.getName()))
                .fileDownloadPath(parseFileUrl(URL_DOWNLOAD, FILE.getName()))
                .fileSize(bytes).build();
        photoRepository.save(photo);
        return photo.toDto();
    }

    private String parseUUID(String fileName) {
        Date now = new Date();
        String[] name = fileName.split("\\.");
        StringBuffer sb = new StringBuffer(name[0]);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String uploadTime = sdf.format(now);

        String extension = fileName.substring(fileName.lastIndexOf("."));
        sb.append(uploadTime);
        sb.append(extension);

        return sb.toString();
    }

    private String parseFileUrl(String urlPath, String fileName) {
        StringBuffer sb = new StringBuffer(ServletUriComponentsBuilder.fromCurrentContextPath().toUriString());
        sb.append(urlPath);
        sb.append(fileName);
        return sb.toString();
    }
}
