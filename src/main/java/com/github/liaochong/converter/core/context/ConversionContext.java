package com.github.liaochong.converter.core.context;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.github.liaochong.converter.annoation.Converter;
import com.github.liaochong.ratel.tools.core.builder.MapBuilder;
import com.github.liaochong.ratel.tools.core.utils.ClassUtil;

/**
 * 转换上下文
 *
 * @author liaochong
 * @version V1.0
 */
public class ConversionContext {

    private static final Log LOG = LogFactory.getLog(ConversionContext.class);

    private static final Map<Condition, Handler> ACTION_MAP = MapBuilder.concurrentHashMap();

    private static boolean enableConverter = false;

    /**
     * 转换starter是否无效
     * 
     * @return true/false
     */
    public static boolean isDisable() {
        return !enableConverter;
    }

    /**
     * 初始化上下文环境
     * 
     * @param scanPackageName 扫描包名称
     * @param converterBeans spring扫描到的bean
     */
    public static void initialize(String scanPackageName, Map<String, Object> converterBeans) {
        if (MapUtils.isEmpty(ACTION_MAP)) {
            LOG.info("start initialize conversion environment");
            enableConverter = true;
            initStaticActionMap(scanPackageName);
            initNonStaticActionMap(converterBeans);
            LOG.info("conversion environment initialization completed");
        }
    }

    /**
     * 初始化静态操作集合
     *
     * @param scanPackageName 扫描路径
     */
    private static void initStaticActionMap(String scanPackageName) {
        Set<Class<?>> set = collectConverterClass(scanPackageName);
        if (CollectionUtils.isEmpty(set)) {
            return;
        }
        set.parallelStream().forEach(clz -> packagingAction(clz.getDeclaredMethods(), null));
    }

    /**
     * 初始化非静态操作集合
     * 
     * @param converterBeans 转换对象
     */
    private static void initNonStaticActionMap(Map<String, Object> converterBeans) {
        if (MapUtils.isEmpty(converterBeans)) {
            return;
        }
        Stream<Object> objectStream = converterBeans.values().parallelStream();
        objectStream.forEach(bean -> packagingAction(bean.getClass().getDeclaredMethods(), bean));
    }

    /**
     * 收集转换对象
     *
     * @return 列表集
     */
    private static Set<Class<?>> collectConverterClass(String scanPackageName) {
        Set<Class<?>> set = ClassUtil.getClassSet(scanPackageName);
        if (CollectionUtils.isEmpty(set)) {
            return Collections.emptySet();
        }
        Predicate<Class<?>> predicate = clazz -> clazz.isAnnotationPresent(Converter.class);
        return set.parallelStream().filter(predicate).collect(Collectors.toSet());
    }

    /**
     * 包装action
     * 
     * @param methods 方法
     * @param handlerBean 处理者
     */
    private static void packagingAction(Method[] methods, Object handlerBean) {
        if (ArrayUtils.isEmpty(methods)) {
            return;
        }
        // 参数唯一，且为public
        Predicate<Method> commonFilter = method -> method.getParameterCount() == 1
                && Modifier.isPublic(method.getModifiers());

        Predicate<Method> staticFilter = method -> Objects.isNull(handlerBean) == Modifier
                .isStatic(method.getModifiers());

        Arrays.stream(methods).filter(commonFilter).filter(staticFilter)
                .forEach(method -> ConversionContext.setAction(method, handlerBean));
    }

    /**
     * 设置action
     * 
     * @param method 转换方法
     * @param handlerBean 转换对象
     */
    private static void setAction(Method method, Object handlerBean) {
        Class<?>[] paramTypes = method.getParameterTypes();
        Class<?> returnType = method.getReturnType();
        Condition condition = new Condition(paramTypes[0], returnType);
        Handler handler = Handler.newHandler(handlerBean, method);
        ACTION_MAP.put(condition, handler);
    }

    /**
     * 获取actionMap
     * 
     * @return Map
     */
    public static Map<Condition, Handler> getActionMap() {
        return ACTION_MAP;
    }

}
