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
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.collections4.CollectionUtils;

import com.github.liaochong.converter.context.ConverterContext;
import com.github.liaochong.converter.context.Handler;
import com.github.liaochong.converter.exception.ConvertException;
import com.github.liaochong.converter.utils.SupplierUtil;
import com.github.liaochong.ratel.tools.core.validator.ObjectValidator;
import lombok.extern.slf4j.Slf4j;

/**
 * Beans转换策略
 *
 * @author liaochong
 * @version 1.0
 */
@Slf4j
class BeansConvertStrategy {

    /**
     * 集合转换，无指定异常提供
     *
     * @param source 需要转换的集合
     * @param targetClass 需要转换到的类型
     * @param nonNullFilter 是否非空过滤
     * @param <E> 转换后的类型
     * @param <T> 转换前的类型
     * @return 结果
     */
    public static <E, T> List<E> convertBeans(List<T> source, Class<E> targetClass, boolean nonNullFilter) {
        return convertBeans(source, targetClass, null, source::stream, nonNullFilter);
    }

    /**
     * 集合转换，无指定异常提供
     *
     * @param source 需要转换的集合
     * @param targetClass 需要转换到的类型
     * @param exceptionSupplier 异常操作
     * @param nonNullFilter 是否非空过滤
     * @param <E> 转换后的类型
     * @param <T> 转换前的类型
     * @param <X> 异常返回类型
     * @return 结果
     */
    public static <E, T, X extends RuntimeException> List<E> convertBeans(List<T> source, Class<E> targetClass,
            Supplier<X> exceptionSupplier, boolean nonNullFilter) {
        return convertBeans(source, targetClass, exceptionSupplier, source::stream, nonNullFilter);
    }

    /**
     * 集合并行转换，无指定异常提供
     *
     * @param source 需要转换的集合
     * @param targetClass 需要转换到的类型
     * @param nonNullFilter 是否非空过滤
     * @param <E> 转换后的类型
     * @param <T> 转换前的类型
     * @return 结果
     */
    public static <E, T> List<E> parallelConvertBeans(List<T> source, Class<E> targetClass, boolean nonNullFilter) {
        return convertBeans(source, targetClass, null, source::parallelStream, nonNullFilter);
    }

    /**
     * 集合并行转换，无指定异常提供
     *
     * @param source 需要转换的集合
     * @param targetClass 需要转换到的类型
     * @param exceptionSupplier 异常操作
     * @param nonNullFilter 是否非空过滤
     * @param <E> 转换后的类型
     * @param <T> 转换前的类型
     * @param <X> 异常返回类型
     * @return 结果
     */
    public static <E, T, X extends RuntimeException> List<E> parallelConvertBeans(List<T> source, Class<E> targetClass,
            Supplier<X> exceptionSupplier, boolean nonNullFilter) {
        return convertBeans(source, targetClass, exceptionSupplier, source::parallelStream, nonNullFilter);
    }

    /**
     * 集合转换
     *
     * @param source 需要转换的集合
     * @param targetClass 需要转换到的类型
     * @param exceptionSupplier 异常操作
     * @param streamSupplier 流
     * @param nonNullFilter 是否非空过滤
     * @param <E> 转换后的类型
     * @param <T> 转换前的类型
     * @param <X> 异常返回类型
     * @return 结果
     */
    private static <E, T, X extends RuntimeException> List<E> convertBeans(List<T> source, Class<E> targetClass,
            Supplier<X> exceptionSupplier, Supplier<Stream<T>> streamSupplier, boolean nonNullFilter) {
        ObjectValidator.ifNullThrow(targetClass, () -> new NullPointerException("TargetClass can not be null"));
        if (CollectionUtils.isEmpty(source)) {
            return SupplierUtil.ifNonNullThrowOrElse(exceptionSupplier, Collections::emptyList);
        }
        // 若异常提供者不为NULL，则先校验是否存在NULL对象，存在则抛出异常
        if (Objects.nonNull(exceptionSupplier)) {
            boolean hasNullObj = streamSupplier.get().anyMatch(Objects::isNull);
            if (hasNullObj) {
                throw exceptionSupplier.get();
            }
        }
        // 一次性获取，避免每次转换都要查找导致的额外消耗
        Optional<T> sourceElement = source.stream().filter(Objects::nonNull).findAny();
        if (!sourceElement.isPresent()) {
            return Collections.emptyList();
        }
        Handler handler = ConverterContext.getActionHandler(sourceElement.get().getClass(), targetClass);
        log.info("Call method \"{}\"", handler.getMethod());

        Stream<T> stream = streamSupplier.get();
        if (nonNullFilter) {
            stream = stream.filter(Objects::nonNull);
        }
        return stream.map(convertedObj -> convertBean(convertedObj, targetClass, handler, exceptionSupplier))
                .collect(Collectors.toList());
    }

    /**
     * 单个Bean转换
     *
     * @throws ConvertException 转换异常
     *
     * @param source 被转换对象
     * @param targetClass 需要转换到的类型
     * @param handler 转换处理者
     * @param exceptionSupplier 异常操作
     * @param <T> 转换前的类型
     * @param <U> 转换后的类型
     * @param <X> 异常返回类型
     * @return 结果
     */
    private static <T, U, X extends RuntimeException> U convertBean(T source, Class<U> targetClass, Handler handler,
            Supplier<X> exceptionSupplier) {
        if (Objects.isNull(source)) {
            return SupplierUtil.ifNonNullThrowOrElse(exceptionSupplier, () -> null);
        }
        try {
            return targetClass.cast(handler.getMethod().invoke(handler.getHandler(), source));
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw ConvertException.of("Call method \"" + handler.getMethod() + "\" failed", e);
        }
    }

}
