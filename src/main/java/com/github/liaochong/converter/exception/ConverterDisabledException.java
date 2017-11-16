package com.github.liaochong.converter.exception;

/**
 * 转换器不可用异常
 *
 * @author liaochong
 * @version V1.0
 */
public class ConverterDisabledException extends RuntimeException {

    public ConverterDisabledException(String message) {
        super(message);
    }

    public ConverterDisabledException(String message, Throwable cause) {
        super(message, cause);
    }

    public static ConverterDisabledException of(String message) {
        return new ConverterDisabledException(message);
    }
}
