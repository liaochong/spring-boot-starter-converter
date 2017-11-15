package com.github.liaochong.converter.core;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.github.liaochong.converter.annoation.Converter;
import com.github.liaochong.ratel.tools.core.builder.MapBuilder;
import com.github.liaochong.ratel.tools.core.utils.ClassUtil;

/**
 * 转换类收集器
 * 
 * @author liaochong
 * @version V1.0
 */
class ConverterCollector {

    /**
     * 获取操作集合
     *
     * @param scanPackageName 扫描路径
     * @return Map
     */
    static Map<ConversionContext, Handler> getActionMap(String scanPackageName) {
        Set<Class<?>> set = collectConverter(scanPackageName);
        ConcurrentHashMap<ConversionContext, Handler> result = MapBuilder.concurrentHashMap();
        set.parallelStream().forEach(clz -> {
            Method[] methods = clz.getDeclaredMethods();
            // 参数唯一，且为public static方法
            Predicate<Method> predicate = method -> method.getParameterCount() == 1
                    && Modifier.isPublic(method.getModifiers()) && Modifier.isStatic(method.getModifiers());
            Arrays.stream(methods).filter(predicate).forEach(method -> {
                Class<?>[] paramTypes = method.getParameterTypes();
                Class<?> returnType = method.getReturnType();
                ConversionContext conversionContext = new ConversionContext(paramTypes[0], returnType);
                Handler handler = new Handler(clz, method);
                result.put(conversionContext, handler);
            });
        });
        return result;
    }

    /**
     * 收集转换对象
     *
     * @return 列表集
     */
    private static Set<Class<?>> collectConverter(String scanPackageName) {
        Set<Class<?>> set = ClassUtil.getClassSet(scanPackageName);
        if (CollectionUtils.isEmpty(set)) {
            return Collections.emptySet();
        }
        Predicate<Class<?>> predicate = clazz -> clazz.isAnnotationPresent(Converter.class);
        return set.parallelStream().filter(predicate).collect(Collectors.toSet());
    }
}
