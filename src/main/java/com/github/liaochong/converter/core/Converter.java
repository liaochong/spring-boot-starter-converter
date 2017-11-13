package com.github.liaochong.converter.core;

import java.util.Map;

import org.apache.commons.collections4.MapUtils;

import lombok.Data;

/**
 * @author liaochong
 * @version V1.0
 */
@Data
public class Converter {

    private Map<ConversionContext, Handler> actionMap;

    /**
     * 转换
     *
     * @param convertedObj 被转换对象
     * @param clz          需要转换到的类型
     * @param <E>          转换后的类型
     * @param <T>          转换前的类型
     * @return 结果
     */
    public <E, T> E convert(T convertedObj, Class<E> clz) {
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
