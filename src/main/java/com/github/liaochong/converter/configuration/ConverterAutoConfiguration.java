package com.github.liaochong.converter.configuration;

import javax.annotation.Resource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.liaochong.converter.annoation.Converter;


/**
 * 自动配置
 *
 * @author liaochong
 * @version V1.0
 */
@Configuration
@EnableConfigurationProperties(ConverterProperties.class)
@ConditionalOnClass(Converter.class)
public class ConverterAutoConfiguration {

    @Resource
    private ConverterProperties converterProperties;

    @Bean
    public ConverterStartListener getConverter() {
        //获取扫描路径
        String scanPackageName = converterProperties.getScanPackageName();
        return new ConverterStartListener(scanPackageName);
    }
}
