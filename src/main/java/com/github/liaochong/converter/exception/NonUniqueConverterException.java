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
 * 非唯一转换方法异常
 *
 * @author liaochong
 * @version 1.0
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
