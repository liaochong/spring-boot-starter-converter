package com.github.liaochong.converter.exception;

/**
 * 无对应的转换器异常
 *
 * @author liaochong
 * @version V1.0
 */
public class NoConverterException extends RuntimeException {

    public NoConverterException(String message) {
        super(message);
    }

    public NoConverterException(String message, Throwable cause) {
        super(message, cause);
    }

    public static NoConverterException of(String message) {
        return new NoConverterException(message);
    }
}
