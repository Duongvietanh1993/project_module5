package com.cinema.service.upload;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class UploadServiceIMPL implements UploadService {
    @Value("${path-upload}")
    private String pathUpload;
    @Value(("${server.port}"))
    private String serverPort;

    @Override
    public String uploadImage(MultipartFile file) {
        String filename = file.getOriginalFilename();
        try {
            FileCopyUtils.copy(file.getBytes(), new File(pathUpload + filename));
            return "http://localhost:" + serverPort + "/" + filename;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
