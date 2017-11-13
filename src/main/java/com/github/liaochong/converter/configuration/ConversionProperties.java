package com.github.liaochong.converter.configuration;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * @author liaochong
 * @version V1.0
 */
@Data
@ConfigurationProperties(prefix = "conversion")
public class ConversionProperties {

    @NotBlank
    String scanPackageName;
}
