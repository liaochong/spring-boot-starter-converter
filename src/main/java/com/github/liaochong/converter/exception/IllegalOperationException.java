package com.github.liaochong.converter.exception;

/**
 * 非法操作异常
 * 
 * @author liaochong
 * @version V1.0
 */
public class IllegalOperationException extends RuntimeException {

    public IllegalOperationException(String message) {
        super(message);
    }

    public IllegalOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public static IllegalOperationException of(String message) {
        return new IllegalOperationException(message);
    }
}
