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

import java.util.List;
import java.util.function.Supplier;

/**
 * Bean 转换器
 *
 * @author liaochong
 * @version 1.0
 */
public final class BeanConverter {

    private BeanConverter() {
    }

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
        return BeansConvertStrategy.convertBeans(source, targetClass, false);
    }

    /**
     * 集合转换
     * 
     * @param source 需要转换的集合
     * @param targetClass 需要转换到的类型
     * @param exceptionSupplier 异常操作
     * @param <E> 转换后的类型
     * @param <T> 转换前的类型
     * @param <X> 异常返回类型
     * @return 结果
     */
    public static <E, T, X extends RuntimeException> List<E> convertIfNullThrow(List<T> source, Class<E> targetClass,
            Supplier<X> exceptionSupplier) {
        return BeansConvertStrategy.convertBeans(source, targetClass, exceptionSupplier, false);
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
        return BeansConvertStrategy.convertBeans(source, targetClass, true);
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
        return BeansConvertStrategy.parallelConvertBeans(source, targetClass, false);
    }

    /**
     * 集合并行转换，出现null值时抛出异常
     * 
     * @param source 需要转换的集合
     * @param targetClass 需要转换到的类型
     * @param exceptionSupplier 异常操作
     * @param <E> 转换后的类型
     * @param <T> 转换前的类型
     * @param <X> 异常返回类型
     * @return 结果
     */
    public static <E, T, X extends RuntimeException> List<E> parallelConvertIfNullThrow(List<T> source,
            Class<E> targetClass, Supplier<X> exceptionSupplier) {
        return BeansConvertStrategy.parallelConvertBeans(source, targetClass, exceptionSupplier, false);
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
        return BeansConvertStrategy.parallelConvertBeans(source, targetClass, true);
    }

    /**
     * 单个Bean转换
     *
     * @param source 被转换对象
     * @param targetClass 需要转换到的类型
     * @param <T> 转换前的类型
     * @param <U> 转换后的类型
     * @return 结果
     */
    public static <T, U> U convert(T source, Class<U> targetClass) {
        return BeanConvertStrategy.convertBean(source, targetClass);
    }

    /**
     * 单个Bean转换
     *
     * @param source 被转换对象
     * @param targetClass 需要转换到的类型
     * @param exceptionSupplier 异常操作
     * @param <T> 转换前的类型
     * @param <U> 转换后的类型
     * @param <X> 异常返回类型
     * @return 结果
     */
    public static <T, U, X extends RuntimeException> U convertIfNullThrow(T source, Class<U> targetClass,
            Supplier<X> exceptionSupplier) {
        return BeanConvertStrategy.convertBean(source, targetClass, exceptionSupplier);
    }

}
