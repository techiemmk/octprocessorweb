package com.cauth.ocr.service;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cauth.ocr.dto.FileUploadRequest;
import com.cauth.ocr.dto.FileUploadResponse;
import com.cauth.ocr.exception.OCRException;
import com.cauth.ocr.helper.S3Helper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Autowired
    S3Helper s3Helper;

    @Override
    public FileUploadResponse uploadFileToS3(FileUploadRequest request) throws OCRException {
        try {
            FileUploadResponse response = new FileUploadResponse();
            response.setFileName(request.getFile().getOriginalFilename());
            response.setFileSize(FileUtils.byteCountToDisplaySize(request.getFile().getSize()));
            response.setContentType(request.getFile().getContentType());
            String s3Path = s3Helper.uploadFileToS3(request.getFile(), getFileKey(request));
            response.setS3URLPath(s3Path);
            return response;
        } catch (Exception exception) {
            log.error("Exception while uploading file to S3", exception);
            throw new OCRException("Exception while uploading file to S3", exception);
        }
    }

    private String getFileKey(FileUploadRequest request) {
        return request.getUserId() + "/" + request.getKeyName();
    }
}
