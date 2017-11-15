package com.github.liaochong.converter.core;

import java.lang.reflect.Method;

import lombok.Data;

/**
 * 转换处理对象
 *
 * @author liaochong
 * @version V1.0
 */
@Data
public class Handler {

    Object handler;

    Method method;

    private Handler(Object handler, Method method) {
        this.handler = handler;
        this.method = method;
    }

    /**
     * 静态工厂方法
     * 
     * @param method 转换方法
     * @return
     */
    public static Handler staticHandler(Method method) {
        return new Handler(null, method);
    }
}
