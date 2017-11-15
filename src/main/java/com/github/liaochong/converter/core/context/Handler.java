package com.github.liaochong.converter.core.context;

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
     * @param handler 处理者
     * @param method 处理方法
     * @return Handler
     */
    public static Handler newHandler(Object handler, Method method) {
        return new Handler(handler, method);
    }
}
