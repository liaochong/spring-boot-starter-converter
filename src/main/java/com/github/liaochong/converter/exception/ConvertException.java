package com.github.liaochong.converter.exception;

/**
 * 转换异常
 * 
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

    public ConvertException(Throwable cause) {
        super(cause);
    }

    public static ConvertException of(String message) {
        return new ConvertException(message);
    }

    public static ConvertException of(Throwable cause) {
        return new ConvertException(cause);
    }

    public static ConvertException of(String message, Throwable cause) {
        return new ConvertException(message, cause);
    }

}
