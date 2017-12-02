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
import com.github.liaochong.ratel.tools.core.validator.ObjectValidator;

/**
 * Bean 转换器
 *
 * @author liaochong
 * @version 1.0
 */
public class BeanConverter {

    /**
     * 集合转换
     *
     * @param source 需要转换的集合
     * @param targetClass 需要转换到的类型
     * @param <E> 转换后的类型
     * @param <T> 转换前的类型
     * @return 结果
     */
    public static <E, T> List<E> convert(List<T> source, Class<E> targetClass) {
        return convertBeans(source, targetClass, null, false, false);
    }

    /**
     * 集合转换
     * 
     * @param source 需要转换的集合
     * @param targetClass 需要转换到的类型
     * @param exceptionSupplier 异常操作
     * @param <E> 转换后的类型
     * @param <T> 转换前的类型
     * @param <G> 异常返回类型
     * @return 结果
     */
    public static <E, T, G extends RuntimeException> List<E> convertIfNullThrow(List<T> source, Class<E> targetClass,
            Supplier<G> exceptionSupplier) {
        return convertBeans(source, targetClass, exceptionSupplier, false, false);
    }

    /**
     * 集合非空转换，会过滤为空的数据
     *
     * @param source 需要转换的集合
     * @param targetClass 需要转换到的类型
     * @param <E> 转换后的类型
     * @param <T> 转换前的类型
     * @return 结果
     */
    public static <E, T> List<E> nonNullConvert(List<T> source, Class<E> targetClass) {
        return convertBeans(source, targetClass, null, true, false);
    }

    /**
     * 集合并行转换
     *
     * @param source 需要转换的集合
     * @param targetClass 需要转换到的类型
     * @param <E> 转换后的类型
     * @param <T> 转换前的类型
     * @return 结果
     */
    public static <E, T> List<E> parallelConvert(List<T> source, Class<E> targetClass) {
        return convertBeans(source, targetClass, null, false, true);
    }

    /**
     * 集合并行转换，出现null值时抛出异常
     * 
     * @param source 需要转换的集合
     * @param targetClass 需要转换到的类型
     * @param exceptionSupplier 异常操作
     * @param <E> 转换后的类型
     * @param <T> 转换前的类型
     * @param <G> 异常返回类型
     * @return 结果
     */
    public static <E, T, G extends RuntimeException> List<E> parallelConvertIfNullThrow(List<T> source,
            Class<E> targetClass, Supplier<G> exceptionSupplier) {
        return convertBeans(source, targetClass, exceptionSupplier, false, true);
    }

    /**
     * 集合非空并行转换
     *
     * @param source 需要转换的集合
     * @param targetClass 需要转换到的类型
     * @param <E> 转换后的类型
     * @param <T> 转换前的类型
     * @return 结果
     */
    public static <E, T> List<E> nonNullParallelConvert(List<T> source, Class<E> targetClass) {
        return convertBeans(source, targetClass, null, true, true);
    }

    /**
     * 集合转换
     *
     * @param source 需要转换的集合
     * @param targetClass 需要转换到的类型
     * @param exceptionSupplier 异常操作
     * @param nonNullFilter 是否非空过滤
     * @param parallelConvert 是否为并行转换
     * @param <E> 转换后的类型
     * @param <T> 转换前的类型
     * @param <G> 异常返回类型
     * @return 结果
     */
    private static <E, T, G extends RuntimeException> List<E> convertBeans(List<T> source, Class<E> targetClass,
            Supplier<G> exceptionSupplier, boolean nonNullFilter, boolean parallelConvert) {
        ObjectValidator.ifNullThrow(targetClass, () -> new NullPointerException("TargetClass can not be null"));
        if (CollectionUtils.isEmpty(source)) {
            return ifNonNullThrowOrElse(exceptionSupplier, Collections::emptyList);
        }
        // 若异常提供者不为NULL，则先校验是否存在NULL对象，存在则抛出异常
        if (Objects.nonNull(exceptionSupplier)) {
            Stream<T> stream = parallelConvert ? source.parallelStream() : source.stream();
            stream.filter(Objects::isNull).findAny().ifPresent(ele -> {
                throw exceptionSupplier.get();
            });
        }
        // 一次性获取，避免每次转换都要查找导致的额外消耗
        Optional<T> sourceElement = source.stream().filter(Objects::nonNull).findAny();
        if (!sourceElement.isPresent()) {
            return Collections.emptyList();
        }
        Handler handler = ConverterContext.getActionHandler(sourceElement.get().getClass(), targetClass);

        Stream<T> stream = parallelConvert ? source.parallelStream() : source.stream();
        if (nonNullFilter) {
            stream = stream.filter(Objects::nonNull);
        }
        return stream.map(convertedObj -> convertBean(convertedObj, targetClass, handler, exceptionSupplier))
                .collect(Collectors.toList());
    }

    /**
     * 单个Bean转换
     *
     * @param source 被转换对象
     * @param targetClass 需要转换到的类型
     * @param <E> 转换后的类型
     * @param <T> 转换前的类型
     * @return 结果
     */
    public static <E, T> E convert(T source, Class<E> targetClass) {
        ObjectValidator.ifNullThrow(targetClass, () -> new NullPointerException("TargetClass can not be null"));
        return convertBean(source, targetClass, null);
    }

    /**
     * 单个Bean转换
     *
     * @param source 被转换对象
     * @param targetClass 需要转换到的类型
     * @param exceptionSupplier 异常操作
     * @param <E> 转换后的类型
     * @param <T> 转换前的类型
     * @param <G> 异常返回类型
     * @return 结果
     */
    public static <E, T, G extends RuntimeException> E convertIfNullThrow(T source, Class<E> targetClass,
            Supplier<G> exceptionSupplier) {
        ObjectValidator.ifNullThrow(targetClass, () -> new NullPointerException("TargetClass can not be null"));
        return convertBean(source, targetClass, exceptionSupplier);
    }

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
    private static <E, T, G extends RuntimeException> E convertBean(T source, Class<E> targetClass,
            Supplier<G> exceptionSupplier) {
        if (Objects.isNull(source)) {
            return ifNonNullThrowOrElse(exceptionSupplier, () -> null);
        }
        Handler handler = ConverterContext.getActionHandler(source.getClass(), targetClass);
        try {
            return targetClass.cast(handler.getMethod().invoke(handler.getHandler(), source));
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw ConvertException.of("Call method \"" + handler.getMethod() + "\" failed", e);
        }
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
     * @param <E> 转换后的类型
     * @param <T> 转换前的类型
     * @param <G> 异常返回类型
     * @return 结果
     */
    private static <E, T, G extends RuntimeException> E convertBean(T source, Class<E> targetClass, Handler handler,
            Supplier<G> exceptionSupplier) {
        if (Objects.isNull(source)) {
            return ifNonNullThrowOrElse(exceptionSupplier, () -> null);
        }
        try {
            return targetClass.cast(handler.getMethod().invoke(handler.getHandler(), source));
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw ConvertException.of("Call method \"" + handler.getMethod() + "\" failed", e);
        }
    }

    /**
     * 如果异常提供非空则抛出异常，否则返回提供内容
     * 
     * @param exceptionSupplier 异常提供者
     * @param provider 内容提供者
     * @param <E> 返回值类型
     * @param <T> 异常类型
     * @return 返回值
     */
    private static <E, T extends RuntimeException> E ifNonNullThrowOrElse(Supplier<T> exceptionSupplier,
            Supplier<E> provider) {
        if (Objects.nonNull(exceptionSupplier)) {
            throw exceptionSupplier.get();
        } else {
            return provider.get();
        }
    }

}
