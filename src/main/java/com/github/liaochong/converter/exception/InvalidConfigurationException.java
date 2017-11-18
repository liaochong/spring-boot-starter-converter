package com.github.liaochong.converter.exception;

/**
 * @author liaochong
 * @version V1.0
 */
public class InvalidConfigurationException extends RuntimeException {

    public InvalidConfigurationException(String message) {
        super(message);
    }

    public InvalidConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    public static InvalidConfigurationException of(String message) {
        return new InvalidConfigurationException(message);
    }
}
