# spring-boot-starter-converter 文档
[![Maven central](https://maven-badges.herokuapp.com/maven-central/com.github.liaochong/spring-boot-starter-converter/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.liaochong/spring-boot-starter-converter)
[![License](http://img.shields.io/:license-apache-brightgreen.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

spring-boot-starter-converter 是一款为简化DO、BO、DTO等Bean之间转换的Spring Boot Starter.

版本支持 | Support Version
------------------

- 0.0.x - only support for Java 8+,Spring Boot

优点 | Advantages
------------------

- **内置性能分析插件**：可输出Sql语句以及其执行时间，建议开发测试时启用该功能，能有效解决慢查询
- **内置全局拦截插件**：提供全表 delete 、 update 操作智能分析阻断，预防误操作

Maven 依赖
-----------------
```xml
<dependency>
    <groupId>com.github.liaochong</groupId>
    <artifactId>spring-boot-starter-converter</artifactId>
    <version>maven 官方最新版本为准</version>
</dependency>
```
