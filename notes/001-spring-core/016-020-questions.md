# **Spring Professional Exam**

## **Dependency Container and IoC**

- ### [Table of Contents](#table-of-contents)

- [Question 16](#question-16) 
- [Question 17](#question-17) 
- [Question 18](#question-18) 
- [Question 19](#question-19) 
- [Question 20](#question-20)


----
### **Question 16** 
- What is the behavior of the annotation @Autowired with regards to field injection, constructor injection and method injection?

- @Autowired is an annotation 
- processed by AutowiredAnnotationBeanPostProcessor,
- can be put onto class 
  - constructor, 
  - field, 
  - setter method or 
  - config method.
- this annotation enables automatic Spring Dependency Resolution that is primary based on types.
```java

// constructor
@Autowired
public RecordsService(
  DbRecordsReader recordsReade,
  DbRecordsProcessor recordsProcessor) {

  }

// field
@Autowired
public DbRecordsReader recordsReader;

// setter method
@Autowired
public void setRecordsReader(DbRecordsReader recordsReader) {}
```
- `@Autowired` has a property `required` which can be used to tell Spring if dependency is required or optional.

- By default dependency is required.
```java
@Autowired(required = false)
private RecordsValidator recordsValidator; 
```
- If `@Autowired`  on top of constructor or method that contains multiple arguments, then 
- all arguments are considered required dependency 
    - unless argument is of type  Optional,
    ```java
    @Autowired
    private Optional<RecordsHash> recordsHash;
    ```
    - is marked as @Nullable, or 
    ```java
    @Autowired
    @Nullable
    private RecordsUtil recordsUtil;
    ```
    - is marked as @Autowired(required = false).
    ```java
    @Autowired(required = false)
    private RecordsValidator recordsValidator; 
    ```

- `@Autowired` on top of Collection or Map .
- Spring will inject all beans matching the type into Collection and key-value pairs as BeanName-Bean into Map. 


```java
@Autowired
public void setRecordsReaders(List<RecordsReader> recordsReaders) {
  System.out.println(getClass().getSimpleName() + "setRecordsReaders:");
  recordsReaders.stream()
        .map(r -> "\t" + r.getClass().getSimpleName())
        .forEach(System.out::println);
}
```


- Order of elements depends on usage of 
- @Order, 
```java
@Component
@Order(1)
public class SocketRecordsReader implements RecordsReader {
	@Override
	public Collection<Record> readRecords() {
		return Collections.emptyList();
	}
}
```
- @Priority annotations and 
```java
@Component
@Priority(2)
public class WebServiceRecordsReader implements RecordsReader {
	@Override
	public Collection<Record> readRecords() {
		return Collections.emptyList();
	}
}
```
- implementation of Ordered interface.

`@Autowired` uses following steps when resolving dependency:

- Match exactly by type, if `only one found`, finish.

- If `multiple beans` of same type found,

  - check if any contains @Primary annotation, 

  - if yes, inject @Primary bean and finish.

- If no exactly one match exists, 

  - check if @Qualifier exists for field, 

  - if yes use @Qualifier to find matching bean.

- If still no exactly one bean found, 

  - narrow the search by using bean name.

- If still no exactly one bean found,

  - throw exception

  - (NoSuchBeanDefinitionException,

  - NoUniqueBeanDefinitionException, ...).

---- 

### **Question 17**
### **What do you have to do, if you would like to inject something into a private field? How does this impact testing?**

- Injection of dependency into private field can be done with @Autowired annotation:
```java
@Autowired
private ReportWriter reportWriter;
```
- Injection of property into private field can be done with @Value annotation:
```java
@Value("${report.global.name}")
private String reportGlobalName;
```
- Private Field cannot be accessed from outside of the class, to resolve this when writing Unit Test you can use following solutions:
 - Use `SpringRunner` with `ContextConfiguration` and `@MockBean`
 ```java

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
public class ReportServiceTest01 {
    @Autowired
    private ReportService reportService;
    @MockBean
    private ReportWriter reportWriter;

    @Test
    public void shouldPassReportToWriter() {
        reportService.execute();

        verify(reportWriter).write(any(Report.class), any());
    }
}
 ```
 - Use `ReflectionTestUtils` to modify private fields
 ```java
 public class ReportServiceTest03 {
    private ReportService reportService;

    @Before
    public void setUp() {
        reportService = new ReportService();
    }

    @Test
    public void shouldPassReportToWriter() {
        ReportWriter reportWriter = Mockito.mock(ReportWriter.class);
        ReflectionTestUtils.setField(reportService, ReportService.class, "reportWriter", reportWriter, ReportWriter.class);

        reportService.execute();

        verify(reportWriter).write(any(Report.class), any());
    }
}
 ```
 - Use MockitoJUnitRunner to inject mocks
 ```java
 @RunWith(MockitoJUnitRunner.class)
public class ReportServiceTest02 {
    @InjectMocks
    private ReportService reportService;
    @Mock
    private ReportWriter reportWriter;

    @Test
    public void shouldPassReportToWriter() {
        reportService.execute();

        verify(reportWriter).write(any(Report.class), any());
    }
}
 ```
 - Use @TestPropertySource to inject test properties into private fields

```java
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
@TestPropertySource(properties = "report.global.name=" + REPORT_NAME)
public class ReportServiceTest04 {
    static final String REPORT_NAME = "Mock_Report";

    @Autowired
    private ReportService reportService;
    @MockBean
    private ReportWriter reportWriter;

    @Test
    public void shouldPassReportToWriter() {
        reportService.execute();

        verify(reportWriter).write(any(Report.class), eq(REPORT_NAME));
    }
}
```

---

### **Question 18** 
### How does the @Qualifier annotation complement the use of @Autowired?

- `@Qualifier` annotation gives you additional control on which bean will be injected, 
  - when multiple beans of the same type are found.
  - By adding additional information on which bean you want to inject,
- `@Qualifier` resolves issues with NoUniqueBeanDefinitionException.

- You can use `@Qualifier` in three ways:
  - At injection point with `bean name` as value
  ```java
  @Component
  public class DbRecordsProcessor implements RecordsProcessor {}

  @Component
  public class FileRecordsProcessor implements RecordsProcessor {}

  @Autowired
  @Qualifier("fileRecordsProcessor")
  private RecordsProcessor recordsProcessor;
  ```
  - At injection and bean definition point
  ```java
  @Component("db-records-reader")
  public class DbRecordsReader implements RecordsReader {}

  @Autowired
  @Qualifier("db-records-reader")
  private RecordsReader recordsReader;
  ```

  ```java
  @Component
  @Qualifier("db-writer")
  public class DbRecordsWriter implements RecordsWriter {}

  @Autowired
  @Qualifier("db-writer")
  private RecordsWriter recordsWriter;

  ```
  - Custom Qualifier Annotation Fefinition
```java

@Qualifier
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RecordsValidatorType {

    RecordsValidatorMode value();

    enum RecordsValidatorMode {
        DB,
        FILE
    }
}

@Component
@RecordsValidatorType(FILE)
public class FileRecordsValidator implements RecordsValidator {

@Autowired
@RecordsValidatorType(FILE)
private RecordsValidator recordsValidator;
```

--- 
### **Question 19** 

### What is a proxy object?

- Proxy Object is an object that adds additional logic on top of object that is being proxied without having to modify code of proxied object.

- Proxy object has the same public methods as object that is being proxied and it should be as much as possible indistinguishable from proxied object. 

- When method is invoked on Proxy Object, additional code, usually before and after sections are invoked, also code from proxied object is invoked by Proxy Object.

### what are the two different types of proxies Spring can create?

- Spring Framework supports two kind of proxies:
 - JDK Dynamic Proxy – used by default if target object implements interface 
 - CGLIB Proxy – use when target does not implement any interface
 
 ###  What are the limitations of these proxies (per type)?
 - Limitations of JDK Dynamic Proxy:
  - Requires proxy object to implement the interface 
  - Only interface methods will be proxied
  - No support for self-invocation

- Limitations of CGLIB Proxy:
  - Does not work for final classes 
  - Does not work for final methods 
  - No support for self-invocation
###  What is the power of a proxy object?
- Proxy Advantages:
  - Ability to change behavior of existing beans without changing original code 
  - Separation of concerns (logging, transactions, security, ...)
### where are the disadvantages?
- Proxy Disadvantages:
  - May create code hard to debug
  - Needs to use unchecked exception for exceptions not declared in original method
  - May cause performance issues if before/after section in proxy code is using IO (Network,
Disk)
  - May cause unexpected equals operator (==) results since Proxy Object and Proxied Object
are two different objects


---
### **Question 20**
### What are the advantages of Java Config?

**Advantages of Java Config over XML Config:**
- Compile-Time Feedback due to Type-checking
- Refactoring Tools for Java without special support/plugins work out of the box with Java
Config (special support needed for XML Config)

**Advantages of Java Config over Annotation Based Config:**
- Separation of concerns
  - beans configuration is separated from beans implementation
- Technology agnostic
  - beans may not depend on concrete IoC/DI implementation
  - makes it easier to switch technology
- Ability to integrate Spring with external libraries
- More centralized location of bean list


### **What are the limitations?**

**Limitations of Java Config:**
- Configuration class cannot be final
- Configuration class methods cannot be final
- All Beans have to be listed, for big applications, 
  - it might be a challenge compared to Component Scanning