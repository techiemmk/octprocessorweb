package com.cauth.ocr.service;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cauth.ocr.helper.S3Helper;
import com.cauth.ocr.reader.PlainTextReader;

/**
 * 
 * @author Manoj Kumar M
 *
 */
@Service
public class OCRReaderServiceImpl implements OCRReaderService {

    @Autowired
    private PlainTextReader plainTextReader;

    @Autowired
    S3Helper s3Helper;

    @Value("${cloud.aws.bucket}")
    String contentBucketName;

    @Override
    public String parseImageFromS3(String fileName) {
        BufferedImage image = null;
        try {
            image = s3Helper.getImageFileFromS3(contentBucketName, fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (image == null) {
            return "Unable to get content";
        }
        return plainTextReader.readData(image);
    }

    @Override
    public String parseImage(String path) throws MalformedURLException {
        BufferedImage image = urlToImage(path);
        if (image == null) {
            return "Unable to get content";
        }
        return plainTextReader.readData(image);
    }

    private BufferedImage urlToImage(String urlPath) {
        try {
            return ImageIO.read(new URL(urlPath));
        } catch (Exception excep) {
            excep.printStackTrace();
        }
        return null;
    }
}
