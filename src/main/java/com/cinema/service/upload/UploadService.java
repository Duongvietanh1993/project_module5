package com.cinema.service.upload;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
String UploadImage(MultipartFile file);
}
