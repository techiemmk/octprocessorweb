package com.cauth.ocr.exception;

public class OCRException extends Exception{

    private static final long serialVersionUID = 2097769571871553097L;

    public OCRException(String message) {
        super(message);
    }

    public OCRException(String message, Throwable exception) {
        super(message, exception);
    }
}
