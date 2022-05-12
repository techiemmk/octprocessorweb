package com.cauth.ocr.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.cauth.ocr.dto.FileUploadRequest;
import com.cauth.ocr.dto.FileUploadResponse;
import com.cauth.ocr.exception.OCRException;
import com.cauth.ocr.service.FileUploadService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class FileUploadController {

    @Autowired
    FileUploadService fileUploadService;

    @GetMapping("/")
    public String loadUploadPage() {
        return "fileUpload";
    }

    @PostMapping(value = "/uiupload", consumes = MULTIPART_FORM_DATA_VALUE, produces = APPLICATION_JSON_VALUE)
    public String fileUploadFromUI(@ModelAttribute FileUploadRequest request, ModelMap responseMap) {
        try {
            FileUploadResponse response = fileUploadService.uploadFileToS3(request);
            responseMap.addAttribute("response", response);
            return "uploadSuccess";
        } catch (OCRException excep) {
            log.error("Exception while uploading data", excep);
            responseMap.addAttribute("error", excep.getMessage());
            return "uploadFailure";
        }
    }

    @PostMapping(value = "/upload", consumes = MULTIPART_FORM_DATA_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<FileUploadResponse> fileUpload(@ModelAttribute FileUploadRequest request) {
        try {
            return new ResponseEntity<FileUploadResponse>(fileUploadService.uploadFileToS3(request), CREATED);
        } catch (OCRException excep) {
            log.error("Exception while uploading data", excep);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
        
    }
}
