package com.github.liaochong.converter.core;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import com.github.liaochong.converter.core.context.Condition;
import com.github.liaochong.converter.core.context.ConverterContext;
import com.github.liaochong.converter.core.context.Handler;
import com.github.liaochong.converter.exception.ConvertException;
import com.github.liaochong.converter.exception.ConverterDisabledException;
import com.github.liaochong.converter.exception.NoConverterException;
import lombok.Data;

/**
 * @author liaochong
 * @version V1.0
 */
@Data
public class BeanConverter {

    /**
     * 集合转换
     *
     * @param data 需要转换的集合
     * @param clz 需要转换到的类型
     * @param <E> 转换后的类型
     * @param <T> 转换前的类型
     * @return 结果
     */
    public static <E, T> List<E> convert(List<T> data, Class<E> clz) {
        if (CollectionUtils.isEmpty(data)) {
            return Collections.emptyList();
        }
        return data.stream().map(convertedObj -> convert(convertedObj, clz)).collect(Collectors.toList());
    }

    /**
     * 集合转换
     *
     * @param data 需要转换的集合
     * @param clz 需要转换到的类型
     * @param <E> 转换后的类型
     * @param <T> 转换前的类型
     * @return 结果
     */
    public static <E, T> List<E> parallelConvert(List<T> data, Class<E> clz) {
        if (CollectionUtils.isEmpty(data)) {
            return Collections.emptyList();
        }
        return data.parallelStream().map(convertedObj -> convert(convertedObj, clz)).collect(Collectors.toList());
    }

    /**
     * 转换
     *
     * @param convertedObj 被转换对象
     * @param clz 需要转换到的类型
     * @param <E> 转换后的类型
     * @param <T> 转换前的类型
     * @return 结果
     */
    public static <E, T> E convert(T convertedObj, Class<E> clz) {
        if (ConverterContext.isDisable()) {
            throw ConverterDisabledException.of("@EnableConverter annotation not enabled");
        }
        Map<Condition, Handler> actionMap = ConverterContext.getActionMap();
        if (MapUtils.isEmpty(actionMap)) {
            throw NoConverterException.of("No object with @Converter annotations was found");
        }
        Condition condition = Condition.newInstance(convertedObj.getClass(), clz);
        Handler handler = actionMap.get(condition);
        if (Objects.isNull(handler)) {
            throw NoConverterException.of("The corresponding conversion method was not found");
        }
        try {
            return clz.cast(handler.getMethod().invoke(handler.getHandler(), convertedObj));
        } catch (Exception e) {
            throw ConvertException.of(e);
        }
    }
}
