package com.github.liaochong.converter.configuration;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * @author liaochong
 * @version V1.0
 */
@Data
@ConfigurationProperties(prefix = "conversion")
class ConverterProperties {

    /**
     * 默认全局扫描
     */
    String scanPackageName = StringUtils.EMPTY;
}
