package com.cauth.ocr.dto;

import lombok.Data;

@Data
public class FileUploadResponse {

    String fileName;
    String fileSize;
    String contentType;
    String s3URLPath;
}
