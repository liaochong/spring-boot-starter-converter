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
 * @version 1.0
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
