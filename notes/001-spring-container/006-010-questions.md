## **Dependency Container and IoC**

- ### [Table of Contents](#table-of-contents)
- [Question 06](#question-06)
- [Question 07](#question-07)
- [Question 08](#question-08)
- [Question 09](#question-09)
- [Question 10](#question-10)

### **Question 06**

## **How are you going to create a new instance of an ApplicationContext?**

## Stand alone Applications:

### **`AnnotationConfigApplicationContext`**

### Example 1

```java
  @ComponentScan
  public class ConfigurationComponentScan {}
```

```java
  AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConfigurationComponentScan.class);
  SpringBean1 bean = context.getBean(SpringBean1.class);
  bean.sayHello();
```

### Example 2

```java
@Configuration
public class ConfigurationStatic {
    @Bean
    public SpringBean1 getSpringBean1() { return new SpringBean1();}
    @Bean
    public SpringBean2 getSpringBean2() { return new SpringBean2();}
    @Bean
    public SpringBean3 getSpringBean3() {return new SpringBean3(); }
}
```

```java
 AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConfigurationStatic.class);
 SpringBean1 bean = context.getBean(SpringBean1.class);
 bean.sayHello();
```

### Example 3  
 pasing packages

```java
 AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.home.beans");
 SpringBean1 bean = context.getBean(SpringBean1.class);
 bean.sayHello();
```

### Exmaple 4  
 using context to scan beans package

```java
 AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
 context.scan("com.home.beans");
 context.refresh();
 SpringBean1 bean = context.getBean(SpringBean1.class);
 bean.sayHello();
```

---

## **`ClassPathXmlApplicationContext`**

- beans.xml file
```XML
 <bean id="springBean1" class="com.home.beans.SpringBean1">
    <property name="springBean2" ref="springBean2"/>
    <property name="springBean3" ref="springBean3"/>
</bean>
<bean id="springBean2" class="com.home.beans.SpringBean2"/>
<bean id="springBean3" class="com.home.beans.SpringBean3"/>

```

```java
 ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/beans.xml");
 SpringBean1 springBean1 = context.getBean(SpringBean1.class);
 springBean1.sayHello();
```

## **`FileSystemXmlApplicationContext`**

```java
String beansXmlLocationOnFilesystem = FileSystemXmlApplicationContextExample.class.getResource("/beans.xml").toExternalForm();
FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext(beansXmlLocationOnFilesystem);
SpringBean1 springBean1 = context.getBean(SpringBean1.class);
springBean1.sayHello();
```

## **Web Applications:**

- Servlet 2 – web.xml, ContextLoaderListener, DispatcherServlet
- Servlet 3 – XmlWebApplicationContext
- Servlet 3 - AnnotationConfigWebApplicationContext

Spring Boot:

- SpringBootConsoleApplication – CommandLineRunner
- SpringBootWebApplication – Embedded Tomcat

---

### **Question 07**

**Can you describe the lifecycle of a Spring Bean in an ApplicationContext?**

- **Context is Created:**

  - Beans Definitions are created based on Spring Bean Configuration.
  - `BeanFactoryPostProcessors` are invoked.

- **Bean is Created:**

  - Instance of Bean is Created.
  - Properties and Dependencies are set.
  - BeanPostProcessor::postProcessBeforeInitialization gets called.
  - @PostConstruct method gets called.
  - InitializingBean::afterPropertiesSet method gets called.
  - @Bean(initMethod) method gets called
  - BeanPostProcessor::postProcessAfterInitialization gets called.

- **Bean is Ready to use.**

- **Bean is Destroyed:**
  - @PreDestroy method gets called.
  - DisposableBean::destroy method gets called.
  - @Bean(destroyMethod) method gets called.

---

### **Question 08**

**How are you going to create an ApplicationContext in an integration test?**

- Make sure that you have spring-test-dependency added:

  ```js
  <dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-test</artifactId>
    <scope>test</scope>
  </dependency>
  ```

- Add Spring Runner to your test.
  ```js
  @RunWith(SpringRunner.class)
  ```
- Add `ContextConfiguration` to your test.
  ```js
  @ContextConfiguration(classes = ApplicationConfiguration.class)
  ```

---

### **Question 09**

**What is the preferred way to close an `ApplicationContext`?**

**Standalone Non-Web Applications**

- Register Shutdown hook by calling ConfigurableApplicationContext
  `registerShutdownHook()`   
**_Recommended way_**

```java
  AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
  context.registerShutdownHook();
  SpringBean1 springBean1 = context.getBean(SpringBean1.class);
  springBean1.sayHello();
```

- try catch example
```java
try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class)) {
SpringBean1 springBean1 = context.getBean(SpringBean1.class);
springBean1.sayHello();
}
```
- Call ConfigurableApplicationContext close method.

```java
  context.close();
```

**Web Application**

- ContextLoaderListener will automatically close context when web container stop web application.

- When you shutdown your server, that ContextLoaderListener's `contextDestroyed` method is invoked.

```java
  public void contextDestroyed(ServletContextEvent event){
    closeWebApplicationContext(event.getServletContext());
    ContextCleanupListener.cleanupAttributes(event.getServletContext());
  }
```

**Spring Boot**

- `ApplicationContext` will be automatically closed
- Shutdown hook will be automatically registered
- `ContextLoaderListener` applies to Spring Boot Web Applications as well.

---

## **Question 10**

## **Can you describe: Dependency injection using Java configuration?**

## **Dependency Injection using Java Configuration:**

- With Java Configuration you need to explicitly define all the beans and need to use `@Autowire` on `@Bean` method level to inject dependencies.

```java
@Configuration
public class ApplicationCOnfiguration {
	@Bean
	@Autowired
	public SpringBean1 springBean1(SpringBean2 springBean2, SpringBean3 springBean3) {
		return new SpringBean1(springBean2, springBean3);
	}
	@Bean
	public SpringBean3 springBean3() {
		return new SpringBean3();
	}
	@Bean
	public SpringBean2 springBean2 () {
		return new SpringBean2();
	}
}
```

---

## **DI using annotations**

Create classes annotated with `@Component` annotations

```java
@Component
public class SpringBean1{}
@Component
public class SpringBean2{}
@Component
public class SpringBean3{}
```

### Define dependencies when required

```java
@Autowired
private SpringBean2 springBean2;

@Autowired
private SpringBean3 springBean3;
```

### Create Configuration component with Component Scanning Enabled

```java
@ComponentScan
public class ApplicationConfiguration { }
```

---

### **Component scanning**

A Process in which Spring is scanning Classpath in search for classes annotated with stereotypes annotations

- @Component, @Repository, @Service, @Controller,etc and
- based on those it creates beans definitions.

Simple component scanning within Configuration package and all subpackages

```java
@ComponentScan
public class ApplicationConfiguration { }
```

**Advanced Component Scanning Rules:**

```java
@ComponentScan(
basePackages = "com.spring.professional.exam.tutorial.modulquestion10.annotations.beans",
//basePackageClasses = SpringBean1.class,
includeFilters = @ComponentScan.Filter(type = FilterTREGEX, pattern = ".*Bean.*"),
excludeFilters = @ComponentScan.Filter(type = FilterTREGEX, pattern = ".*Bean1.*")
)
public class ApplicationConfigurationAdvanced {}
```

---

### **Stereotypes**

**`Stereotypes` Definition**

- Stereotypes are annotations applied to classes to describe role which will be performed by this class.

- Spring discovered classes annotated by stereotypes and creates bean definitions based on those types.

**Types of stereotypes:**

- `Component` – generic component in the system, root stereotype, candidate for autoscanning
- `Service` – class will contain business logic
- `Repository` – class is a data repository (used for data access objects, persistence)
- `Controller` – class is a controller, usually a web controller (used with @RequestMapping)

---

### **Meta-Annotations**

Meta-annotations are annotations that can be used to create new annotations.

- Example of Meta-Annotation
  - `@RestController` annotation is using `@Controller` and `@ResponseBody` to define its behavior.

```java
@Target({ElementType.TYPE}) @Retention(RetentionPolicy.RUNTIME) @Documented
@Controller
@ResponseBody
public @interface RestController {
    @AliasFor(annotation = Controller.class)
  String value() default "";
}
```

---

### **Scopes of Spring beans?**

- **Singleton**
  - Single Bean per Spring Container - Default
- **Prototype**
  - New Instance each time Bean is Requested
- **Request**
  - New Instance per each HTTP Request
- **Session**
  - New Instance per each HTTP Session
- **Application**
  - One Instance per each ServletContext
- **Websocket**
  - One Instance per each WebSocket

---
