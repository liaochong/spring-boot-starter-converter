# spring-boot-starter-converter 文档
[![Maven central](https://maven-badges.herokuapp.com/maven-central/com.github.liaochong/spring-boot-starter-converter/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.liaochong/spring-boot-starter-converter)
[![License](http://img.shields.io/:license-apache-brightgreen.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

spring-boot-starter-converter 是一款为简化DO、BO、DTO等Bean之间转换的Spring Boot Starter.

版本支持 | Support Version
------------------

- 0.0.x - only support for Java 8+,Spring Boot

优点 | Advantages
------------------

- **统一各个转换类名称**：针对每个业务单元，其转换类名称可全部统一，如Converter
- **转换时无需记住方法名称**：直接使用如BeanConverter.convert(source,target.class)这样的方式转换

Maven 依赖
-----------------
```xml
<dependency>
    <groupId>com.github.liaochong</groupId>
    <artifactId>spring-boot-starter-converter</artifactId>
    <version>maven 官方最新版本为准</version>
</dependency>
```
