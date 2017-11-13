package com.github.liaochong.converter.exception;

/**
 * @author liaochong
 * @version V1.0
 */
public class ConvertException extends RuntimeException {

    public ConvertException(String message) {
        super(message);
    }

    public ConvertException(String message, Throwable cause) {
        super(message, cause);
    }
}
