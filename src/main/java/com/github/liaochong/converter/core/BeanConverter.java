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

    private static final int MAX_CONVERT_SIZE = 1000;

    private static Map<ConversionContext, Handler> actionMap;

    /**
     * 初始化操作map
     *
     * @param scanPackageName
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
     * @param clz  需要转换到的类型
     * @param <E>  转换后的类型
     * @param <T>  转换前的类型
     * @return 结果
     */
    public static <E, T> List<E> convert(List<T> data, Class<E> clz) {
        if (CollectionUtils.isEmpty(data)) {
            return Collections.emptyList();
        }
        if (data.size() > MAX_CONVERT_SIZE) {
            return data.parallelStream().map(convertedObj -> convert(convertedObj, clz)).collect(Collectors.toList());
        } else {
            return data.stream().map(convertedObj -> convert(convertedObj, clz)).collect(Collectors.toList());
        }
    }

    /**
     * 转换
     *
     * @param convertedObj 被转换对象
     * @param clz          需要转换到的类型
     * @param <E>          转换后的类型
     * @param <T>          转换前的类型
     * @return 结果
     */
    public static <E, T> E convert(T convertedObj, Class<E> clz) {
        if (MapUtils.isEmpty(actionMap)) {
            return null;
        }
        ConversionContext conversionContext = new ConversionContext(convertedObj.getClass(), clz);
        Handler handler = actionMap.get(conversionContext);
        try {
            return clz.cast(handler.getMethod().invoke(null, convertedObj));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
