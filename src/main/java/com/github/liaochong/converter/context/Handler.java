/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.liaochong.converter.context;

import java.lang.reflect.Method;

import lombok.Data;

/**
 * 转换处理对象
 *
 * @author liaochong
 * @version 1.0
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
    public static Handler newInstance(Object handler, Method method) {
        return new Handler(handler, method);
    }
}
