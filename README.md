# spring-boot-starter-converter 文档
[![Maven central](https://maven-badges.herokuapp.com/maven-central/com.github.liaochong/spring-boot-starter-converter/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.liaochong/spring-boot-starter-converter)
[![License](http://img.shields.io/:license-apache-brightgreen.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

spring-boot-starter-converter 是一款为简化DO、BO、DTO等Bean之间转换的Spring Boot Starter.

版本支持 | Support Version
------------------

- 0.0.x - only support for Java 8+,Spring Boot

优点 | Advantages
------------------

- **可统一各个转换类名称**：针对每个业务单元，其转换类名称可全部统一，如Converter，避免取名的烦恼
- **转换时无需记住转换方法名称**：直接使用如BeanConverter.convert(source,target.class)这样的方式转换，无需记住长长的名称
- **自动完成列表类型转换**：不必手动创建列表类型转换方法，converter会自动帮你完成

Maven 依赖
------------------
```xml
<dependency>
    <groupId>com.github.liaochong</groupId>
    <artifactId>spring-boot-starter-converter</artifactId>
    <version>maven 官方最新版本为准</version>
</dependency>
```
示例 | Example
------------------

1. 使用注解 `com.github.liaochong.converter.annoation.EnableConverter` 标明启用converter-starter

```java
@SpringBootApplication
@EnableConverter
public class ExampleWarApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExampleWarApplication.class, args);
    }
}
```

2. 使用注解 `com.github.liaochong.converter.annoation.Converter` 标明转换类

```java
@Converter
public class UserConverter{

   public static UserBO convertDO2BO(UserDO user){
          UserBO result = new UserBO();
          result.setName(user.getName());
          return result;
   }
}
```
3. 使用类 `com.github.liaochong.converter.core.BeanConverter` 进行转换，参数类别依次为：转换源对象，转换目标类类型

- 单个对象转换
```java
UserDO userDO = new UserDO();
UserBO user = BeanConverter.convert(userDO , UserBO.class);
```
- 列表对象转换
```java
List<UserDO> list = new ArrayList<>();
UserDO userDO1 = new UserDO();
UserDO userDO2 = new UserDO();
list.add(userDO1);
list.add(userDO2);

List<UserBO> users = BeanConverter.convert(list , UserBO.class);
```
- 列表对象`并行`转换
```java
List<UserDO> list = new ArrayList<>();
UserDO userDO1 = new UserDO();
UserDO userDO2 = new UserDO();
list.add(userDO1);
list.add(userDO2);

List<UserBO> users = BeanConverter.parallelConvert(list , UserBO.class);
```
配置 | Configuration
--------------------
1. 设置spring-boot-starter-converter的扫描路径：在application.properties中设置扫描路径，支持多个路径，如 `bean.conversion.scan-packages=com.test.core,com.test.biz.dao`，若不设置，默认全局扫描

异常 | Exception
-------------------
