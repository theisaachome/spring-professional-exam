##

- [Question 21](#question-21)
- [Question 22](#question-22)
- [Question 23](#question-23)
- [Question 24](#question-24)
- [Question 25](#question-25)

---

### **What does the @Bean annotation do?**

`@Bean` annotation is used in `@Configuration` class to inform Spring that the instance of class returned by a method annotated with `@Bean`  return bean that is  managed by Spring.

`@Bean` also allows you to:  
Specify init method : will be called after instance is created and assembled.

```java
public class SpringBean{
    private void init() {
      System.out.println(getClass().getSimpleName() + "::init()");
  }
}
```

```java
@Configuration
public class ApplicationConfig {
    @Bean(initMethod = "init") // specify init method
    @Autowired
    public SpringBean springBean(SpringBean2 springBean2) {
        return new SpringBean(springBean2);
    }
}
```

Specify destroy method: will be called when bean is discarded (usually when context is getting closed).

```java
public class SpringBean {
  // this method name must match on @Bean annotation
  private void destroy() {
      System.out.println(getClass().getSimpleName() + "::destroy()");
}
```

```java
  // this destroyMethod name must match from POJO(Bean) instance method
  @Bean(destroyMethod = "destroy")
  public SpringBean springBean() {
      return new SpringBean();
  }
```

Specify name for the bean by default bean has name auto generated based on method name.

```java
  // this method name is  used in dependencies injection
  @Bean
  public SpringBean3 springBean3(){ return new SpringBean3();}
```

```java
 @Bean
 @Autowired
  public SpringBean springBean(SpringBean3 springBean3) {
      // auto generated method name is used.
      return new SpringBean1(springBean3);
  }
```

This can be overridden by specify alias/aliases for the bean.
```java
   @Bean(name="mySpecialBean") // giving custom or alias for bean name.
   public SpringBean3 springBean3() {
       return new SpringBean3();
   }
```
```java
  @Bean(name = {"myBean", "yourBean", "herBean"}) // giving  aliases for bean name.
  public SpringBean3 springBean3B() {
      return new SpringBean3B();
  }
```
```java
@Bean
@Autowired
 public SpringBean springBean(SpringBean3 mySpecialBean,SpringBean3 myBean) {
     // alias bean name is used.
     return new SpringBean1(mySpecialBean,myBean);
 }
```

Specify if Bean should be used as candidate for injection into other beans – default true.
```java
@Bean(autowireCandidate = true)
public StorageDao springBean() {
      return new DatabaseStorageImpl();
}
```
`autowireCandidate` set false will not be injected.
```java
@Bean(autowireCandidate = false)
public StorageDao springBean() {
      return new FileStorageImpl();
}
```

Configure Autowiring mode – by name or type (Deprecated since Spring 5.1)

---

## **Question 22**

## **What is the default bean id if you only use @Bean?**

When using `@Bean` without specifying name or alias, default bean id will be created based on name of the method which was annotated with `@Bean` annotation.

## **How can you override this?**

You can override this behavior by specifying name or aliases for the bean.

```java
@Bean
public SpringBean1 springBean1() {
    return new SpringBean1();
}
// Example usage
public SpringBean1(SpringBean1 springBean1){}
```

```java
@Bean(name = "2ndSpringBean")
public SpringBean2 springBean2() {
    return new SpringBean2();
}
// Example usage
public SpringBean1(SpringBean2 2ndSpringBean){}
```

```java
@Bean(name = {"3rdSpringBean", "thirdSpringBean"})
public SpringBean3 springBean3() {
    return new SpringBean3();
}
// Example usage
public SpringBean1(SpringBean3 3rdSpringBean){}
```

---

## **Question 23**

## **Why are you not allowed to annotate a final class with `@Configuration?`**

Class annotated with `@Configuration` cannot be final because Spring will use `CGLIB` to create a proxy for `@Configuration` class.
`CGLIB` creates subclass for each class that is supposed to be proxied, Since final class cannot have subclass ,  `CGLIB` will fail.

## **Why can’t @Bean methods be final either?**

Spring needs to override methods from parent class for proxy to work correctly, final method cannot be overridden, having such a method will make CGLIB fail.

If `@Configuration` class will be final or will have final method, Spring will throw `BeanDefinitionParsingException`.

## **How do @Configuration annotated classes support singleton beans?**

Spring supports Singleton beans in `@Configuration` class by creating `CGLIB` proxy that intercepts calls to the method.

Before method is executed from the proxied class, proxy intercept a call and checks if instance of the bean already exists, if instance of the bean exists, then call to method is not allowed and return an existing instance.

If instance does not exists, then call is allowed, bean is created and instance is returned and saved for future reuse.

To make method call interception `CGLIB` proxy needs to create subclass and also needs to override methods.

## **How to observe `@Configuration` class being proxied?**

Easiest way to observe that calls to original `@Configuration` class are proxied is with usage of debugger or by printing stacktrace.

When looking at stacktrace you will notice that class which serves beans is not original class written by you but it is different class, which name contains `$$EnhancerBySpringCGLIB`.


----

## **Question 24**

## **How do you configure `@Profiles`**

### Spring Profiles are configured by:

- Specifying which beans are part of which profile
  ```java
  @Component
  @Profile("database")
  class DatabaseStoreFinancialReportWriter{}
  ```
- Specifying which profiles are active
  ```java
   context.getEnvironment().setActiveProfiles("database");
  ```

### You can specify beans being part of profile in following ways:

### 1. `@Component` class level

- A bean will be part of profile/profiles specified in annotation.

  ```java
  @Component
  @Profile("database")
  class DatabaseStorageDao implements AbstractDAO{}
  ```

  ```java
  @Component
  @Profile("file")
  class FileStorageDao implements AbstractDAO{}
  ```

  ```java
   AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.registerShutdownHook();
  // Activate profile
  context.getEnvironment().setActiveProfiles("database");
  // switching between profiles.
  context.getEnvironment().setActiveProfiles("file");

  context.register(ApplicationConfiguration.class);
  context.refresh();
  ```

### 2. `@Configuration` class level

All beans from this configuration will be part of profile/profiles specified in annotation.

```java
@Configuration
@Profile("database")
public class DatabaseApplicationConfiguration {}
```

```java
@Configuration
@Profile("file")
public class FileApplicationConfiguration {}
```

```java
@Configuration
@Import({DatabaseApplicationConfiguration.class, FileApplicationConfiguration.class})
public class ApplicationConfiguration {}
```

```java
 // Activate profile
 context.getEnvironment().setActiveProfiles("file");
 context.getEnvironment().setActiveProfiles("database");
 context.register(ApplicationConfiguration.class);
 context.refresh();
```

### 3. `@Bean` method of `@Configuration` class\*\*

An instance of bean returned by this method will be part of profile/profiles specified in annotation.

```java
@Configuration
public class ApplicationConfiguration {
    @Bean
    @Profile("database")
    public FinancialDataDao databaseStoreFinancialDataDao() {
        return new DatabaseStoreFinancialDataDao();
    }
    @Bean
    @Profile("file")
    public FinancialDataDao fileStoreFinancialDataDao() {
        return new FileStoreFinancialDataDao();
    }
```

```java
 // Activate profile
 context.getEnvironment().setActiveProfiles("database");
 context.register(ApplicationConfiguration.class);
 context.refresh();
```

### Custom Annotation -@Component/@Configuration/

`@Bean` method annotated with custom annotation will be part of profile/profiles specified in annotation.

```java
@Profile("database")
public @interface DatabaseProfile {}
```

```java
@Profile("file")
public @interface FileProfile {}
```

```java
@Component
@FileProfile
class FileStorageDataDao{}
```

```java
@Component
@DatabaseProfile
class DatabaseStorageDataDao{}
```

```java
@Configuration
@ComponentScan
public class ApplicationConfiguration {}

// Activate profile
context.getEnvironment().setActiveProfiles("file");
context.register(ApplicationConfiguration.class);
context.refresh();
```

## You can activate profiles in following way:

### Programmatically with usage of `ConfigurableEnvironment`.

```java
AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
 context.registerShutdownHook()
 // Activate profile
 context.getEnvironment().setActiveProfiles("file");
 context.register(ApplicationConfig.class);
 context.refresh();
```

### By using `spring.profiles.activeproperty`.

```java
// profile should be activated with VM property
-Dspring.profiles.active=database
```

### On JUnit Test level by using `@ActiveProfiles` annotation.

```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
@ActiveProfiles("database")
public class ApplicationConfigTest {
    @Autowired
    private FinancialDataDao financialDataDao;
}
```

### In SpringBoot Programmatically by usage of `SpringApplicationBuilder`.

```java
 new SpringApplicationBuilder(Runner.class)
                .profiles("database")
                .run(args);
```

### In SpringBoot by `application.properties` or on yml level.

```properties
spring.profiles.active=database
```

```java
@SpringBootApplication
public class Runner implements CommandLineRunner {
    @Autowired
    private FinancialDataDao financialDataDao;
}
```

## What are possible use cases where they might be useful?

### Spring Profiles are useful in following cases:

Changing Behavior of the system in Different Environments by changing set of Beans that are part of specific environments, for example prod, cert, dev.

Changing Behavior of the system for different customers.

Changing set of Beans used in Development Environment and also during Testing Execution.

Changing set of Beans in the system when monitoring or additional debugging capabilities should be turned on.

---

## **Questioon 25**

### Can you use @Bean together with @Profile?

Yes, `@Bean` annotation can be used together with `@Profile` inside class annotated with `@Configuration` annotation on top of method that returns instance of the bean.

If, method annotated with `@Bean` does not have `@Profile`, that beans that this bean will exists in all profiles.

```java
 @Bean
 public EmployeeDao employeeDao() {
     return new EmployeeDao();
 }
```

You can specify one profile.

```java
    @Bean
    @Profile("database")
    public DataReader dbDataReader() {
        return new DbDataReader();
    }
```

You can specify multiple profiles.

```java
  @Bean
  @Profile({"database", "file"})
  public DataProcessor multiSourceDataProcessor() {
      return new MultiSourceDataProcessor();
  }
```

Profile in which bean should not exists:  
Using a not (!) operator.

```java
  @Bean
  @Profile("!prod")
  public DataWriter devDataWriter() {
      return new DevDataWriter();
  }
```

Activating all profiles.

```java
// Activate profile
context.getEnvironment().setActiveProfiles("database", "dev");
context.register(ApplicationConfiguration.class);
context.refresh();
```
