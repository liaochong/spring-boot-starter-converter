package com.github.liaochong.converter.configuration;

import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * @author liaochong
 * @version V1.0
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@ConfigurationProperties(prefix = "bean.conversion")
class ConverterProperties {

    /**
     * 若不填，默认全局扫描
     */
    Set<String> scanPackages;

    public void setScanPackages(Set<String> scanPackages) {
        if (CollectionUtils.isNotEmpty(scanPackages)) {
            this.scanPackages = scanPackages.stream().filter(StringUtils::isNotBlank).map(StringUtils::trim)
                    .collect(Collectors.toSet());
        } else {
            this.scanPackages = scanPackages;
        }
    }

}
