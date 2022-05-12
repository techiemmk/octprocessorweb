package com.cauth.ocr.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class FileUploadRequest {

    private String userId;
    private String fileName;
    private String keyName;
    private MultipartFile file;
}
