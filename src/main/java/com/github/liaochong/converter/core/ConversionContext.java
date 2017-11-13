package com.github.liaochong.converter.core;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * 转换上下文
 *
 * @author liaochong
 * @version V1.0
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConversionContext {

    Class<?> beConvertedClass;

    Class<?> convertResultClass;

    public ConversionContext(Class<?> beConvertedClass, Class<?> convertResultClass) {
        this.beConvertedClass = beConvertedClass;
        this.convertResultClass = convertResultClass;
    }
}
