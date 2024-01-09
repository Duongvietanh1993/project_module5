package com.cinema.controller;

import com.cinema.service.upload.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadController {
    @Autowired
    private UploadService uploadService;

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("img") MultipartFile file) {
        String urlFile = uploadService.UploadImage(file);
        return new ResponseEntity<>("Thanh cong " + urlFile, HttpStatus.OK);
    }
}
