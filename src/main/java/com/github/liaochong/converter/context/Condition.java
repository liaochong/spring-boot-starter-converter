package com.github.liaochong.converter.context;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * 转换条件
 *
 * @author liaochong
 * @version V1.0
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
class Condition {

    Class<?> sourceClass;

    Class<?> targetClass;

    private Condition(Class<?> sourceClass, Class<?> targetClass) {
        this.sourceClass = sourceClass;
        this.targetClass = targetClass;
    }

    /**
     * 静态工厂方法
     * 
     * @param sourceClass 源类
     * @param targetClass 目标类
     * @return Condition
     */
    static Condition newInstance(Class<?> sourceClass, Class<?> targetClass) {
        return new Condition(sourceClass, targetClass);
    }
}
