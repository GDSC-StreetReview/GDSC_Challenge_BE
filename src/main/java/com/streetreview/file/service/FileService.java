package com.streetreview.file.service;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface FileService {

    ResponseEntity<Resource> loadFileAsResource(HttpServletRequest request, String fileName);

    ResponseEntity<Resource> showImage(Map<String, String> param);


}
