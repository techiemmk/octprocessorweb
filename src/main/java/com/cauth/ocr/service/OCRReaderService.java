package com.cauth.ocr.service;

import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cauth.ocr.reader.PlainTextReader;

/**
 * 
 * @author Manoj Kumar M
 *
 */
@Service
public class OCRReaderService {

    @Autowired
    private PlainTextReader plainTextReader;

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
