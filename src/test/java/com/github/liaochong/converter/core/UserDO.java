package com.github.liaochong.converter.core;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * @author liaochong
 * @version V1.0
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDO {

    String name;

    Integer age;

    boolean isMan;

}
