package com.cauth.ocr.service;

import com.cauth.ocr.dto.FileUploadRequest;
import com.cauth.ocr.dto.FileUploadResponse;
import com.cauth.ocr.exception.OCRException;

public interface FileUploadService {

    FileUploadResponse uploadFileToS3(FileUploadRequest request) throws OCRException;
}
