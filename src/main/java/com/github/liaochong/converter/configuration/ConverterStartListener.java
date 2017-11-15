package com.github.liaochong.converter.configuration;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.github.liaochong.converter.annoation.Converter;
import com.github.liaochong.converter.core.context.ConversionContext;

/**
 * 启动监听器
 * <p>
 * 用于扫描指定包，如未指定，默认全局扫描
 * </p>
 * 
 * @author liaochong
 * @version V1.0
 */
public class ConverterStartListener implements ApplicationListener<ContextRefreshedEvent> {

    @Resource
    private ConverterProperties converterProperties;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        Map<String, Object> converterBeans = contextRefreshedEvent.getApplicationContext()
                .getBeansWithAnnotation(Converter.class);
        ConversionContext.initialize(converterProperties.getScanPackageName(), converterBeans);
    }
}
