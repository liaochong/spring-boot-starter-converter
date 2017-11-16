package com.github.liaochong.converter.configuration;

import java.util.Set;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * @author liaochong
 * @version V1.0
 */
@Data
@ConfigurationProperties(prefix = "bean.conversion")
class ConverterProperties {

    /**
     * 若不填，默认全局扫描
     */
    Set<String> scanPackages;
}
