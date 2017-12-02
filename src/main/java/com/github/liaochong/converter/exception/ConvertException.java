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
