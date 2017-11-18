package com.github.liaochong.converter.exception;

/**
 * 无效参数异常
 *
 * @author liaochong
 * @version V1.0
 */
public class InvalidParameterException extends RuntimeException {

    public InvalidParameterException(String message) {
        super(message);
    }

    public InvalidParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    public static InvalidParameterException of(String message) {
        return new InvalidParameterException(message);
    }
}
