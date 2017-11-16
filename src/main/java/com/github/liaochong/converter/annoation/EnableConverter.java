package com.github.liaochong.converter.annoation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import com.github.liaochong.converter.configuration.ConverterAutoConfiguration;

/**
 * @author liaochong
 * @version V1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(ConverterAutoConfiguration.class)
@Documented
public @interface EnableConverter {
}
