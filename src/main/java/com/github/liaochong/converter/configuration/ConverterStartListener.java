package com.github.liaochong.converter.configuration;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.github.liaochong.converter.core.BeanConverter;
import lombok.Data;

/**
 * @author liaochong
 * @version V1.0
 */
@Data
public class ConverterStartListener implements ApplicationListener<ContextRefreshedEvent> {

    private String scanPackageName;

    ConverterStartListener(String scanPackageName) {
        this.scanPackageName = scanPackageName;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        BeanConverter.initActionMap(scanPackageName);
    }
}
