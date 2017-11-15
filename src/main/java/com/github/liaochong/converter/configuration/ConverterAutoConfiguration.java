package com.github.liaochong.converter.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.liaochong.converter.annoation.Converter;
import com.github.liaochong.converter.annoation.EnableConverter;

/**
 * 自动配置
 *
 * @author liaochong
 * @version V1.0
 */
@Configuration
@EnableConfigurationProperties(ConverterProperties.class)
@ConditionalOnClass({ Converter.class, EnableConverter.class })
public class ConverterAutoConfiguration {

    @Bean
    public ConverterStartListener getConverter() {
        return new ConverterStartListener();
    }
}
