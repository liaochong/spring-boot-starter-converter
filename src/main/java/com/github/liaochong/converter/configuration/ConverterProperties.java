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
 * 属性对象
 *
 * @author liaochong
 * @version V1.0
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@ConfigurationProperties(prefix = "bean.conversion")
public class ConverterProperties {

    /**
     * 若不填，默认全局扫描
     */
    Set<String> scanPackages;

    /**
     * 只扫描静态转换方法
     */
    boolean onlyScanStaticMethod = false;

    /**
     * 只扫描非静态转换方法
     */
    boolean onlyScanNonStaticMethod = false;

    /**
     * 是否为严格模式 如果为严格，则当无任何转换方法时抛出异常
     */
    boolean strictMode = false;

    public void setScanPackages(Set<String> scanPackages) {
        if (CollectionUtils.isNotEmpty(scanPackages)) {
            this.scanPackages = scanPackages.stream().filter(StringUtils::isNotBlank).map(StringUtils::trim)
                    .collect(Collectors.toSet());
        } else {
            this.scanPackages = scanPackages;
        }
    }

}
