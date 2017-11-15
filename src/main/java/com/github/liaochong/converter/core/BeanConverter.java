package com.github.liaochong.converter.core;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import com.github.liaochong.converter.core.context.Condition;
import com.github.liaochong.converter.core.context.ConversionContext;
import com.github.liaochong.converter.core.context.Handler;
import com.github.liaochong.converter.exception.ConvertException;
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
     * 转换
     *
     * @param convertedObj 被转换对象
     * @param clz 需要转换到的类型
     * @param <E> 转换后的类型
     * @param <T> 转换前的类型
     * @return 结果
     */
    public static <E, T> E convert(T convertedObj, Class<E> clz) {
        Map<Condition, Handler> actionMap = ConversionContext.getActionMap();
        if (MapUtils.isEmpty(actionMap)) {
            throw ConvertException.of("未找到任何拥有@Converter注解的转换对象");
        }
        Condition condition = new Condition(convertedObj.getClass(), clz);
        Handler handler = actionMap.get(condition);
        try {
            return clz.cast(handler.getMethod().invoke(handler.getHandler(), convertedObj));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
