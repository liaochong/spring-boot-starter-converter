package com.github.liaochong.converter.exception;

/**
 * 非唯一转换方法异常
 *
 * @author liaochong
 * @version V1.0
 */
public class NonUniqueConverterException extends RuntimeException {

    public NonUniqueConverterException(String message) {
        super(message);
    }

    public NonUniqueConverterException(String message, Throwable cause) {
        super(message, cause);
    }

    public static NonUniqueConverterException of(String message) {
        return new NonUniqueConverterException(message);
    }
}
