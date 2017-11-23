# spring-boot-starter-converter 文档
[![Build Status](https://travis-ci.org/liaochong/spring-boot-starter-converter.svg?branch=master)](https://travis-ci.org/liaochong/spring-boot-starter-converter)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.liaochong/spring-boot-starter-converter/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.liaochong/spring-boot-starter-converter)
[![License](http://img.shields.io/:license-apache-brightgreen.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

spring-boot-starter-converter 是一款为简化DO、BO、DTO等Bean之间转换过程的Spring Boot Starter.

版本支持 | Support Version
------------------

- 0.0.x - only support for Java 8+,Spring Boot

优点 | Advantages
------------------

- **降低耦合**：所有转换方法由转换器上下文管理，改变方法签名或者位置等不需要更新同步调用代码；
- **可统一各个转换类名称**：针对每个业务单元，其转换类名称可全部统一，如Converter，避免取名的烦恼；
- **转换时无需记住转换方法名称**：直接使用如BeanConverter.convert(source,target.class)这样的方式转换，无需关心转换方法的名称；
- **自动完成列表类型转换**：不必手动创建列表类型转换方法，converter会自动帮你完成，可自行选择是否并行转换；
- **支持非静态方法**：支持非静态转换方法，避免手动引入Bean，简化代码；

Maven 依赖
------------------
```xml
<dependency>
    <groupId>com.github.liaochong</groupId>
    <artifactId>spring-boot-starter-converter</artifactId>
    <version>0.0.1</version>
</dependency>
```

使用条件 | Condition
------------------
1. 转换方法必须为 `public` 修饰符修饰，否则，不会被注册；
2. 转换方法只能有一个参数入参，多个参数的方法不会被注册；
3. 同一类型的参数、返回类型只能有一个注册方法，如有多个，会在启动阶段抛出异常-`NonUniqueConverterException`； 
4. 必须使用注解 `com.github.liaochong.converter.annoation.EnableConverter` 标明启用converter-starter，否则starter不会进行相应的初始化工作；

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
1. （可选-OPTIONAL）bean.conversion.scan-packages：设置扫描路径，支持多个路径，如 `bean.conversion.scan-packages=com.test.core,com.test.biz.dao`，以英文“,”分隔，若不设置，`默认全局扫描`；
2. （可选-OPTIONAL）bean.conversion.only-scan-static-method：设置是否只扫描静态方法，如 `bean.conversion.only-scan-static-method=true`，若不设置，默认为 `false`；
3. （可选-OPTIONAL）bean.conversion.only-scan-non-static-method：设置是否只扫描非静态方法，如 `bean.conversion.only-scan-non-static-method=true`，若不设置，默认为 `false`；
4. （可选-OPTIONAL）bean.conversion.strict-mode：设置是否启用严格模式，如`bean.conversion.strict-mode=true`，严格模式下，当不存在任何转换方法时项目启动过程抛出异常，否则，当不存在任何转换方法时只会在运行时使用抛出异常，若不设置，默认为 `false`；

接口 | Interface
-------------------
序号 | 接口签名 | 说明
----|------|----
1 | `public static <E, T> E convert(T source, Class<E> targetClass)`| 单个Bean转换
2 | `public static <E, T> List<E> convert(List<T> source, Class<E> targetClass)`| 列表Beans顺序转换
3 | `public static <E, T> List<E> nonNullConvert(List<T> source, Class<E> targetClass)` | 列表Beans非空（过滤NULL对象）转换
4 | `public static <E, T> List<E> parallelConvert(List<T> source, Class<E> targetClass)`| 列表Beans并行转换
5 | `public static <E, T> List<E> nonNullParallelConvert(List<T> source, Class<E> targetClass)` | 列表Beans非空（过滤NULL对象）并行转换
6 | `public static <E, T, G extends RuntimeException> E convertIfNullThrow(T source, Class<E> targetClass,Supplier<G> supplier)` | 单个Bean转换，如果转换对象为NULL，抛出指定异常，如果未指定异常（NULL），则效果同convert
7 | `public static <E, T, G extends RuntimeException> List<E> convertIfNullThrow(List<T> source, Class<E> targetClass,Supplier<G> supplier)` | 列表Beans转换，如果`全部`或`存在`转换对象为NULL，抛出指定异常，如果未指定异常（NULL），则效果同convert
8 | `public static <E, T, G extends RuntimeException> List<E> parallelConvertIfNullThrow(List<T> source,Class<E> targetClass, Supplier<G> supplier)` | 列表Beans并行转换，如果`全部`或`存在`转换对象为NULL，抛出指定异常，如果未指定异常（NULL），则效果同parallelConvert


异常 | Exception
-------------------
1. NonUniqueConverterException：非唯一转换方法异常，该异常出现在发现 `多个转换方法转换同一类型对象到同一目标对象` 的情况；
2. ConverterDisabledException：转换器不可用异常，该异常出现出现的原因是使用了BeanConverter的各个方法，但是未使用注解 `com.github.liaochong.converter.annoation.EnableConverter` 标明启用converter-starter；
3. NoConverterException：无转换方法异常，该异常出现的原因是使用了BeanConverter的方法，但查找不到对应的转换方法；
4. UnsupportedOperationException：非法操作异常，该异常出现的原因是使用了转换上下文不可手动使用的方法；
5. InvalidConfigurationException：无效配置异常，该异常出现的原因是配置冲突导致的无效，如同时设置 `bean.conversion.only-scan-static-method=true` 和 `bean.conversion.only-scan-non-static-method=true`导致无法扫描任何转换器；
6. NullPointerException：空指针异常，该异常出现的原因是输入了不合法的参数，如 `convert(UserDO user, null)`，目标类类型不可为NULL；
7. ConvertException：调用转换方法过程中发生的异常；
