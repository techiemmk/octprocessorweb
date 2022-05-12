package com.cauth.ocr.helper;

import static com.cauth.ocr.constant.Constants.EMPTY_STRING;
import static com.cauth.ocr.constant.Constants.SYMBOL_DELIMITER_FORWARD_SLASH;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.cauth.ocr.exception.OCRException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class S3Helper {

    @Autowired
    AmazonS3 s3Client;

    @Value("${cloud.aws.bucket}")
    String contentBucketName;

    public BufferedImage getImageFileFromS3(String bucketName, String fileName) throws IOException {
        //bucketName = "test-cauth-wc-data-01";
        //fileName = "Capture.JPG";
        S3Object s3object = s3Client.getObject(bucketName, fileName);
        if (s3object == null) {
            return null;
        }
        return ImageIO.read(s3object.getObjectContent());
    }

    public String uploadFileToS3(MultipartFile file, String keyName) throws OCRException {
        if (Objects.isNull(file) || !StringUtils.hasText(keyName)) {
            log.error("MultipartFile is null or empty or the keyName value is empty");
            return null;
        }
        String key = getObjectKey(file, keyName);
        return uploadFileContentToS3(file, key);
    }

    public String[] uploadFileToS3(MultipartFile[] files, String keyName) throws OCRException {
        if (Objects.isNull(files) || files.length == 0 || !StringUtils.hasText(keyName)) {
            log.error("MultipartFile array is null or empty or the keyName value is empty");
            return null;
        }
        List<String> s3UrlPathList = new ArrayList<>();
        for (MultipartFile file : files) {
            if (file == null) {
                s3UrlPathList.add(EMPTY_STRING);
            } else {
                String key = getObjectKey(file, keyName);
                s3UrlPathList.add(uploadFileToS3(file, key));
            }
        }
        return s3UrlPathList.stream().toArray(String[]::new);
    }

    private String getObjectKey(MultipartFile file, String keyName) {
        return new StringBuilder(contentBucketName).append(SYMBOL_DELIMITER_FORWARD_SLASH).append(keyName)
                .append(SYMBOL_DELIMITER_FORWARD_SLASH).append(file.getOriginalFilename()).toString();
    }

    private String uploadFileContentToS3(MultipartFile file, String key) throws OCRException {
        String s3UrlPath = EMPTY_STRING;
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());
            PutObjectRequest putObjectRequest = new PutObjectRequest(contentBucketName, key, file.getInputStream(),
                    metadata);
            putObjectRequest.setMetadata(metadata);
            s3Client.putObject(putObjectRequest);
            s3UrlPath = s3Client.getUrl(contentBucketName, key).toString();
        } catch (Exception exception) {
            log.error("Exception occurred while uploading content to S3", exception);
            throw new OCRException(exception.getMessage(), exception);
        }
        return s3UrlPath;
    }
}
