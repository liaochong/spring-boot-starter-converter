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
package com.github.liaochong.converter.utils;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Supplier工具
 *
 * @author liaochong
 * @version 1.0
 */
public class SupplierUtil {

    /**
     * 如果异常提供非空则抛出异常，否则返回提供内容
     *
     * @param exceptionSupplier 异常提供者
     * @param provider 内容提供者
     * @param <T> 返回值类型
     * @param <X> 异常类型
     * @return 返回值
     */
    public static <T, X extends RuntimeException> T ifNonNullThrowOrElse(Supplier<X> exceptionSupplier,
            Supplier<T> provider) {
        if (Objects.nonNull(exceptionSupplier)) {
            throw exceptionSupplier.get();
        } else {
            return provider.get();
        }
    }
}
