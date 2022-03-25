
# **Spring Professional Exam**

## **Dependency Container and IoC**

- ### [Table of Contents](#table-of-contents)

- [Question 11](#Question-11)
- [Question 12](#Question-12) 
- [Question 13](#Question-13) 
- [Question 14](#Question-14) 
- [Question 15](#Question-15) 


### **Question 11**

## **Are beans lazily or eagerly instantiated by default?** 


### **Lazy and Eager Instance Creation vs Scope Type:**

- `Singleton` Beans are eagerly instantiated by default.
  ```java
  @Component //by default it is singleton bean
  public class SpringBean {}
  ```
- `Prototype` Beans are lazily instantiated by default.

  ```java
  @Component
  @Scope("prototype")
  public class SpringBean {}
  ```
- (instance is created when bean is requested)
  ```java
  context.getBean(SpringBean.class);
  ```

If Singleton Bean has dependency on Prototype Bean,then Prototype Bean Instance will be created eagerly to satisfy dependencies for Singleton Bean.

```java
@Component
public class StudentService {
    @Autowired
    private StudentRepo studentRepo;
}
@Component
@Scope("prototype")
public class StudentRepo {}
// StudentRepo is created early to satisfy dependencies
```


## **How do you alter this behavior?**

### **Altering Behavior:**

### 1. You can change default behavior for all beans by `@ComponentScan` annotation

  ```java
  //by default lazyInit is false
  @ComponentScan(lazyInit = true)
  public class ApplicationConfiguration {}
  ```
  - Setting `lazyInit` to true, will make all beans lazy, even Singleton Beans

  - Setting `lazyInit` to false (default), will create Singleton Beans Eagerly and Prototype Beans Lazily.


### 2. You can also change default behavior by using `@Lazy` annotation:

- By default `@Lazy` is used to mark bean as lazily instantiated
  ```java
  @Component
  @Lazy
  public class Computer {}
  ```


- `@Lazy` annotation takes one parameter .Whether lazy initialization should occur.

- You can use `@Lazy(false)` to force Eager Instantiation.  

- use case for `@ComponentScan(lazyInit = true)` when some beans always needs to be instantiated eagerly.

  ```java
  @Component
  @Lazy(false)
  public class Computer {}
  ```

### 3. `@Lazy` can be applied to:

- Classed annotated with `@Component`. Makes bean Lazy or as specified by `@Lazy` parameter
  ```java
  @Component
  @Lazy
  public class Computer {}
  ```
- Classes annotated with `@Configuration` annotation to make all beans provided by configuration lazy or as specified by `@Lazy` parameter.
  ```java
  @Configuration
  @Lazy
  public class ApplicationConfiguration {
      @Bean
      public SpringBean springBean() {
          return new SpringBean();
      }
  }
  ```

- `Method` annotated with `@Bean` annotation. Makes bean created by method Lazy or as specified by `@Lazy` parameter.
  ```java
  @Configuration
  @Lazy
  public class ApplicationConfiguration {
      @Bean
      @Lazy(value = false)
      public SpringBean springBean() {
          return new SpringBean();
      }
  }
  ```
---

### **Question 12**
### **What is a property source? How would you use @PropertySource?**

- PropertySource is Spring Abstraction on Environment Key-Value pairs,

- which can come from:

  - JVM Properties

  - System Environmental Variables

  - JNDI Properties

  - Servlet Parameters

  - Properties File Located on File system 

  - Properties File Located on Classpath

- You read properties with usage of @PropertySource or @PropertySources annotation:
  
```java
@PropertySources({ 
  @PropertySource("file:/someurl/app-db.properties"), 
  @PropertySource("classpath:/app-defaults.properties")
})
@ComponentScan
public class ApplicationConfiguration {
}
```
- You access properties with usage of @Value annotation:

```java
@Value("${db.host}")
private String dbHost;
```

---

### **Question 13**
#### **What is a `BeanFactoryPostProcessor`?.** 

- `BeanFactoryPostProcessor` is an interface that contains single method postProcessBeanFactory,
- implementing it allows you to create logic that will modify Spring Bean Metadata before any Bean is created.

###  **what is it used for?** 
- `BeanFactoryPostProcessor` does not create any beans, however it can access and alter Metadata that is used later to create Beans.

###  **When is it invoked?**

- `BeanFactoryPostProcessor` is invoked after Spring will read or discover Bean Definitions, 
- but before any Spring Bean is created.

### **Why would you define a static `@Bean` method?**

- Because `BeanFactoryPostProcessor` is also a Spring Bean, but a special kind of Bean that should be invoked before other types of beans get created, Spring needs to have ability to create it before any other beans.

- This is why `BeanFactoryPostProcessors` needs to be registered from static method level.

```java
@Bean
public static CustomBeanFactoryPostProcessor customerBeanFactoryPostProcessor() {
   return new CustomBeanFactoryPostProcessor();
}
```

###  **What is a `ProperySourcesPlaceholderConfigurer` used for?**

- PropertySourcesPlaceholderConfigurer is a `BeanFactoryPostProcessor` 

- Use to resolve properties placeholder

- Use on fields annotated with
 `@Value("${property_name}")`.
 ```java
 @Value("${app.env}") 
 private String appEnv; 
 @Value("${app.envid}") 
 private String appEnvId;
 ```

----------

### **Question 14** 
### **What is a BeanPostProcessor?.** 

- An interface to create extensions to Spring Framework 

- That will modify Spring Beans objects during initialization.

- This interface contains two methods:

  - postProcessBeforeInitialization

  - postProcessAfterInitialization

###  **What do they do for?**
- By Implementing  you can 
  - modify _created and assembled bean_ objects or 
  - even switch object that will represent the bean.

### **How is it different to a BeanFactoryPostProcessor?** 
 - Main difference
    - BeanFactoryPostProcessor `works with Bean Definitions` while
    - BeanPostProcessor works with `Bean Objects`.

BeanFactoryPostProcessor and BeanPostProcessor in Spring Container Lifecycle :
1. Beans Definitions are created based on Spring Bean Configuration.
2. BeanFactoryPostProcessors are invoked.
3. Instance of Bean is Created.
4. Properties and Dependencies are set.
5. BeanPostProcessor::postProcessBeforeInitialization gets called.
6. @PostConstruct method gets called.
7. InitializingBean::afterPropertiesSet method gets called.
8. @Bean(initMethod) method gets called
9. BeanPostProcessor::postProcessAfterInitialization gets called.


- Recommended way to define BeanPostProcessor
- static @Bean method in Application Configuration.
- it should be created early, before other Beans Objects are ready.
```java
@Bean
public static CustomBeanPostProcessor customBeanPostProcessor() {
   return new CustomBeanPostProcessor();
}
```
- It is also possible to create `BeanPostProcessor` through regular registration in Application Configuration or
- through Component Scanning and `@Component` annotation,
- however because in that case bean can be created late in processes, recommended way is options provided above.

### What is an initialization method and how is it declared on a Spring bean?
- A method that you can write for Spring Bean 
- To perform some initialization code 
  - that depends on properties (and/or)
  - dependencies injected into Spring Bean.

- You can declare Initialization method in three ways:
  - Create method in Spring Bean annotated with @PostConstruct
  - Implement InitializingBean::afterPropertiesSet
  - Create Bean in Configuration class with @Bean method and use @Bean(initMethod)


### What is a destroy method, how is it declared?

- A method in Spring Bean that you can use to implement any cleanup logic for resources used by the Bean. 

- Method will be called when Spring Bean will be taken out of use, this is usually happening when Spring Context is closed.

- You can declare destroy method in following ways:
  - Create method annotated with @PreDestroy annotation
  - Implement DisposableBean::destroy
  - Create Bean in Configuration class with @Bean method and use @Bean(destroyMethod)

### Consider how you enable JSR-250 annotations like @PostConstruct and @PreDestroy?
- When using `AnnotationConfigApplicationContext` support for `@PostConstruct` and `@PreDestroy` is added automatically.

- Those annotations are handled by `CommonAnnotationBeanPostProcessor` with is automatically registered by `AnnotationConfigApplicationContext`


### When/how will they (initialization, destroy methods) get called?
- Context is Created:
  1. Beans Definitions are created based on Spring Bean Configuration.
  2. BeanFactoryPostProcessors are invoked.
- Bean is Created:
  1. Instance of Bean is Created.
  2. Properties and Dependencies are set.
  3. BeanPostProcessor::postProcessBeforeInitialization gets called.
  4. @PostConstruct method gets called.
  5. InitializingBean::afterPropertiesSet method gets called.
  6. @Bean(initMethod) method gets called
  7. BeanPostProcessor::postProcessAfterInitialization gets called.
- Bean is Ready to use.
- Bean is Destroyed (usually when context is closed):
  1. @PreDestroy method gets called.
  2. DisposableBean::destroy method gets called.
  3. @Bean(destroyMethod) method gets called.

-------

### **Question 15**
### **What does component-scanning do?**
- ComponentScanning
  - Process in which Spring is scanning Classpath
  - in search for classes annotated with stereotypes annotations 
    - @Component, 
    - @Repository, 
    - @Service, 
    - @Controller, ...and 
  - based on those creates beans definitions.
- Simple component scanning within Configuration package and all subpackages
```java

@ComponentScan
public class ApplicationConfiguration { }
```
- AdvancedComponentScanningRules
```java
@ComponentScan(
basePackages = "com.spring.professional.exam.tutorial.module01.question15.advanced.beans", 
includeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*Bean"),
excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*(Controller|Service).*")
)
public class ApplicationConfiguration { }
```
