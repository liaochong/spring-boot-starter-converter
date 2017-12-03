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
package com.github.liaochong.converter.core;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.function.Supplier;

import com.github.liaochong.converter.context.ConverterContext;
import com.github.liaochong.converter.context.Handler;
import com.github.liaochong.converter.exception.ConvertException;
import com.github.liaochong.converter.utils.SupplierUtil;
import com.github.liaochong.ratel.tools.core.validator.ObjectValidator;
import lombok.extern.slf4j.Slf4j;

/**
 * Bean转换策略
 *
 * @author liaochong
 * @version 1.0
 */
@Slf4j
class BeanConvertStrategy {

    /**
     * 单个Bean转换
     *
     * @throws ConvertException 转换异常
     *
     * @param source 被转换对象
     * @param targetClass 需要转换到的类型
     * @param exceptionSupplier 异常操作
     * @param <E> 转换后的类型
     * @param <T> 转换前的类型
     * @param <G> 异常返回类型
     * @return 结果
     */
    public static <E, T, G extends RuntimeException> E convertBean(T source, Class<E> targetClass,
            Supplier<G> exceptionSupplier) {
        ObjectValidator.ifNullThrow(targetClass, () -> new NullPointerException("TargetClass can not be null"));
        if (Objects.isNull(source)) {
            return SupplierUtil.ifNonNullThrowOrElse(exceptionSupplier, () -> null);
        }
        Handler handler = ConverterContext.getActionHandler(source.getClass(), targetClass);
        log.info("Call method \"{}\"", handler.getMethod());
        try {
            return targetClass.cast(handler.getMethod().invoke(handler.getHandler(), source));
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw ConvertException.of("Call method \"" + handler.getMethod() + "\" failed", e);
        }
    }

}
