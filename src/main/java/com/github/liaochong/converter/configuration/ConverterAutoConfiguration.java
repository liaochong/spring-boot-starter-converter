package com.github.liaochong.converter.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.liaochong.converter.core.Converter;


/**
 * 自动配置
 *
 * @author liaochong
 * @version V1.0
 */
@Configuration
@ConditionalOnClass(com.github.liaochong.converter.annoation.Converter.class)
public class ConverterAutoConfiguration {

    @Bean
    public Converter getConverter() {
        return new Converter();
    }
}
