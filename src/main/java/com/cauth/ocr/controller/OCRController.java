package com.cauth.ocr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cauth.ocr.service.OCRReaderService;

@RestController
@RequestMapping("/ocr")
public class OCRController {

    @Autowired
    private OCRReaderService ocrReaderService;

    @GetMapping("/parseData")
    public String parseData(@RequestParam(name = "path", defaultValue = "http://jeroen.github.io/images/testocr.png") String path) {
        try {
            return ocrReaderService.parseImage(path);
        } catch (Exception excep) {
            excep.printStackTrace();
        }
        return "Invalid image data";
    }

    @GetMapping("/parseDataFromS3")
    public String parseDataFromS3(@RequestParam(name = "fileName", defaultValue = "CAPTURE.png") String fileName) {
        try {
            return ocrReaderService.parseImageFromS3(fileName);
        } catch (Exception excep) {
            excep.printStackTrace();
        }
        return "Invalid image data";
    }
}
