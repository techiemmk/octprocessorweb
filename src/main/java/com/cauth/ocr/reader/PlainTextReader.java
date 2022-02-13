package com.cauth.ocr.reader;

import java.awt.image.BufferedImage;
import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.TesseractException;

/**
 * 
 * @author Manoj Kumar M
 *
 */
@Component
public class PlainTextReader {

    @Autowired
    ITesseract tesseract;

    public String readData(String path) {
        try {
            return tesseract.doOCR(new File(path));
        } catch (TesseractException excep) {
            excep.printStackTrace();
        }
        return null;
    }

    public String readData(BufferedImage image) {
        try {
            return tesseract.doOCR(image);
        } catch (TesseractException excep) {
            excep.printStackTrace();
        }
        return null;
    }
}
