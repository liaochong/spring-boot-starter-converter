package com.github.liaochong.converter.core;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import lombok.Data;

/**
 * @author liaochong
 * @version V1.0
 */
@Data
public class BeanConverter {

    private static Map<ConversionContext, Handler> actionMap;

    /**
     * 初始化操作map
     *
     * @param scanPackageName 扫描包名称
     */
    public static void initActionMap(String scanPackageName) {
        if (MapUtils.isEmpty(actionMap)) {
            actionMap = ConverterCollector.getActionMap(scanPackageName);
        }
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
        if (MapUtils.isEmpty(actionMap)) {
            return null;
        }
        ConversionContext conversionContext = new ConversionContext(convertedObj.getClass(), clz);
        Handler handler = actionMap.get(conversionContext);
        try {
            return clz.cast(handler.getMethod().invoke(handler.getHandler(), convertedObj));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
