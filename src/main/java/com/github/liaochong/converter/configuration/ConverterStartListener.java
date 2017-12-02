/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.liaochong.converter.configuration;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.github.liaochong.converter.annoation.Converter;
import com.github.liaochong.converter.context.ConverterContext;

/**
 * 启动监听器
 * <p>
 * 用于扫描指定包，如未指定，默认全局扫描
 * </p>
 * 
 * @author liaochong
 * @version 1.0
 */
public class ConverterStartListener implements ApplicationListener<ContextRefreshedEvent> {

    @Resource
    private ConverterProperties converterProperties;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        ApplicationContext applicationContext = contextRefreshedEvent.getApplicationContext();
        Map<String, Object> converterBeans = applicationContext.getBeansWithAnnotation(Converter.class);
        ConverterContext.initialize(converterProperties, converterBeans);
    }
}
