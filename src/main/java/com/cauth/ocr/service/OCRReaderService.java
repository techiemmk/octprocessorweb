package com.cauth.ocr.service;

import java.net.MalformedURLException;

public interface OCRReaderService {

    public String parseImage(String path) throws MalformedURLException;
    public String parseImageFromS3(String fileName);
}
