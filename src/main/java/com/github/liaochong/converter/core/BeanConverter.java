package com.github.liaochong.converter.core;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.github.liaochong.converter.context.Condition;
import com.github.liaochong.converter.context.ConverterContext;
import com.github.liaochong.converter.context.Handler;
import com.github.liaochong.converter.exception.ConvertException;
import com.github.liaochong.converter.exception.ConverterDisabledException;
import com.github.liaochong.converter.exception.InvalidParameterException;
import com.github.liaochong.converter.exception.NoConverterException;
import com.github.liaochong.ratel.tools.core.validator.BooleanValidator;
import com.github.liaochong.ratel.tools.core.validator.MapValidator;
import com.github.liaochong.ratel.tools.core.validator.ObjectValidator;

/**
 * Bean 转换器
 *
 * @author liaochong
 * @version V1.0
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
        if (CollectionUtils.isEmpty(source)) {
            return Collections.emptyList();
        }
        return source.stream().map(convertedObj -> convert(convertedObj, targetClass)).collect(Collectors.toList());
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
        if (CollectionUtils.isEmpty(source)) {
            return Collections.emptyList();
        }
        return source.stream().filter(Objects::nonNull).map(convertedObj -> convert(convertedObj, targetClass))
                .collect(Collectors.toList());
    }

    /**
     * 转换
     *
     * @param source 被转换对象
     * @param targetClass 需要转换到的类型
     * @param <E> 转换后的类型
     * @param <T> 转换前的类型
     * @return 结果
     */
    public static <E, T> E convert(T source, Class<E> targetClass) {
        if (Objects.isNull(source)) {
            return null;
        }
        ObjectValidator.ifNullThrow(targetClass, () -> InvalidParameterException.of("targetClass can not be null"));

        BooleanValidator.ifTrueThrow(ConverterContext.isDisable(),
                () -> ConverterDisabledException.of("@EnableConverter annotation not enabled"));

        Map<Condition, Handler> actionMap = ConverterContext.getActionMap();
        MapValidator.ifEmptyThrow(actionMap,
                () -> NoConverterException.of("No object with @Converter annotations was found"));

        Condition condition = Condition.newInstance(source.getClass(), targetClass);
        Handler handler = actionMap.get(condition);
        ObjectValidator.ifNullThrow(handler,
                () -> NoConverterException.of("The corresponding conversion method was not found"));

        try {
            return targetClass.cast(handler.getMethod().invoke(handler.getHandler(), source));
        } catch (Exception e) {
            throw ConvertException.of(e);
        }
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
        if (CollectionUtils.isEmpty(source)) {
            return Collections.emptyList();
        }
        return source.parallelStream().map(convertedObj -> convert(convertedObj, targetClass))
                .collect(Collectors.toList());
    }
}
