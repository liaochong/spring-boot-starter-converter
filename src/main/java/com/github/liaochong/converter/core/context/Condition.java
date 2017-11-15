package com.github.liaochong.converter.core.context;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * @author liaochong
 * @version V1.0
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Condition {

    Class<?> beConvertedClass;

    Class<?> convertResultClass;

    public Condition(Class<?> beConvertedClass, Class<?> convertResultClass) {
        this.beConvertedClass = beConvertedClass;
        this.convertResultClass = convertResultClass;
    }
}
