package com.github.liaochong.converter.core;

import java.lang.reflect.Method;

import lombok.Data;

/**
 * @author liaochong
 * @version V1.0
 */
@Data
public class Handler {

    Class<?> targetClass;

    Method method;

    public Handler(Class<?> targetClass, Method method) {
        this.targetClass = targetClass;
        this.method = method;
    }
}
