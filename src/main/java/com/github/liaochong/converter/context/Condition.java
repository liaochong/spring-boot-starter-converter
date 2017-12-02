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
package com.github.liaochong.converter.context;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * 转换条件
 *
 * @author liaochong
 * @version V1.0
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
class Condition {

    Class<?> sourceClass;

    Class<?> targetClass;

    private Condition(Class<?> sourceClass, Class<?> targetClass) {
        this.sourceClass = sourceClass;
        this.targetClass = targetClass;
    }

    /**
     * 静态工厂方法
     * 
     * @param sourceClass 源类
     * @param targetClass 目标类
     * @return Condition
     */
    static Condition newInstance(Class<?> sourceClass, Class<?> targetClass) {
        return new Condition(sourceClass, targetClass);
    }
}
