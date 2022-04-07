# **Spring Professional Exam**

## **Dependency Container and IoC**

- ### [Table of Contents](#table-of-contents)

- [Question 16](#question-16) 
- [Question 17](#question-17) 
- [Question 18](#question-18) 
- [Question 19](#question-19) 
- [Question 20](#question-20)


----
## **Question 16** 

## **What is the behavior of the annotation @Autowired?**


`@Autowired` is an annotation that is processed by `AutowiredAnnotationBeanPostProcessor`, which can be put onto class constructor, field, setter method or config method. Using this annotation enables automatic Spring Dependency Resolution that is primary based on types.


`@Autowired` has a property `required` which can be used to tell Spring if dependency is required or optional. By default dependency is required.

```java
@Autowired(required = false)
private RecordsValidator recordsValidator; 

@Autowired
private StudentRepo studentRepo;
```

If `@Autowired`  on top of constructor or method that contains multiple arguments, then all arguments are considered required dependency.

```java
@Autowired
public RecordsService(
  DbRecordsReader recordsReader,
  DbRecordsProcessor recordsProcessor) {
     this.recordsReader = recordsReader;
     this.recordsProcessor = recordsProcessor;
}
```

The arguments of type  `Optional`, `@Nullable` and `@Autowired(required = false)` are  not considered as required dependency.

```java
 @Autowired
 public RecordService(
    DbRecordsReader recordsReader,
    DbRecordsProcessor recordsProcessor,
    Optional<RecordsUtil> recordsUtil,
    @Nullable RecordsHash recordsHash,
    @Autowired(required = false) RecordsValidator recordsValidator) {}
```

If `@Autowired` is used on top of Collection or Map then Spring will inject all beans matching the type into Collection and key-value pairs as BeanName-Bean into Map.

```java
@Autowired
public void setRecordsReaders(List<RecordsReader> recordsReaders) {
  System.out.println(getClass().getSimpleName() + "setRecordsReaders:");
  recordsReaders.stream()
        .map(r -> "\t" + r.getClass().getSimpleName())
        .forEach(System.out::println);
}
```

`@Autowired` uses following steps when resolving dependency:

  1. Match exactly by type, if only one found, finish.

  2. If multiple beans of same type found, check if any contains `@Primary` annotation, if yes, inject `@Primary` bean and finish.

  3. If no exactly one match exists, check if `@Qualifier` exists for field, if yes use `@Qualifier` to find matching bean.

  4. If still no exactly one bean found, narrow the search by using bean name.

  5. If still no exactly one bean found, throw exception (NoSuchBeanDefinitionException,NoUniqueBeanDefinitionException,...)

## **`@Autowired` can be used with constructor like this:**

```java
@Autowired
public RecordsService(
  DbRecordsReader recordsReader,
  DbRecordsProcessor recordsProcessor) {
     this.recordsReader = recordsReader;
     this.recordsProcessor = recordsProcessor;
}
```
Constructor can have any access modifier (public, protected, private, package-private).

If there is only one constructor in class, there is no need to use `@Autowired` on top of it, Spring will use this default constructor anyway and will inject dependencies into it.

```java
public EmployeeService(
  EmployeeRepo employeeRepo,
  SalaryService salaryService) {
     this.employeeRepo = employeeRepo;
     this.salaryService = salaryService;
}
```
If class defines multiple constructor, then you are obligated to use `@Autowired` to tell Spring which constructor should be used to create Spring Bean.
```java

public EmployeeService(
  EmployeeRepo employeeRepo,
  SalaryService salaryService) {
     this.employeeRepo = employeeRepo;
     this.salaryService = salaryService;
}
@Autowired
public EmployeeService(
  EmployeeRepo employeeRepo) {
     this.employeeRepo = employeeRepo;
}
```
 If you will have a class with multiple constructor without any of constructor marked as @Autowired then Spring will throw `NoSuchMethodException`.

By default all arguments in constructor are required, however you can use Optional, @Nullable or @Autowired(required = false) to indicate that parameter is not required.
```java
@Autowired
private RecordsService(
  DbRecordsReader recordsReader,
  Optional<RecordsUtil> recordsUtil,
  @Nullable RecordsHash recordsHash,
  @Autowired(required = false) RecordsValidator recordsValidator) {}
```
      
## **@Autowired with field injection is used like this:**

Autowired fields can have any visibility level. 

  ```java
  @Autowired
  public DbRecordsReader recordsReader;
  @Autowired
  protected DbRecordsBackup recordsBackup; 
  @Autowired
  private DbRecordsProcessor recordsProcessor; 
  @Autowired
  DbRecordsWriter recordsWriter;
  ```
Injection is happening after Bean is created but before any init method (@PostConstruct,
InitializingBean, @Bean(initMethod)) is called.  

By default field is required, however you can use Optional, @Nullable or @Autowired(required = false) to indicate that field is not required.

  ```java
  @Autowired
  private Optional<RecordsHash> recordsHash;
  @Autowired
  @Nullable
  private RecordsUtil recordsUtil;
  @Autowired(required = false)
  private RecordsValidator recordsValidator;
  ```


## @Autowired can be used with method injection like this:

```java
@Autowired
public void setRecordsReader(DbRecordsReader recordsReader) { this.recordsReader = recordsReader;
}
```
`@Autowired` method can have any visibility level and also can contain multiple parameters.

If method contains multiple parameters, then by default it is assumed that in `@Autowired` method all parameters are required.

If Spring will be unable to resolve all dependencies for this method, NoSuchBeanDefinitionException or `NoUniqueBeanDefinitionException` will be thrown.

When using `@Autowired(required = false)` with method, it will be invoked only if Spring can resolve all parameters.

If you want Spring to invoke method only with arguments partially resolved, you need to use @Autowired method with parameter marked as `Optional`,` @Nullable` or `@Autowired(required = false)` to indicate that this parameter is not required.

Order of elements depends on usage of `@Order`, `@Priority` annotations and implementation of Ordered interface.

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
`@Priority` annotations usage
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


---- 

### **Question 17**
### **What do you have to do, if you would like to inject something into a private field?**


Injection of dependency into private field can be done with `@Autowired` annotation:

```java
@Autowired
private ReportWriter reportWriter;
```

Injection of property into private field can be done with `@Value` annotation:

```java
@Value("${report.global.name}")
private String reportGlobalName;
```

Private Field cannot be accessed from outside of the class, to resolve this when writing Unit Test you can use following solutions:

### Use `SpringRunner` with `ContextConfiguration` and `@MockBean`
```java
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
public class ReportServiceTest {
    @Autowired
    private ReportService reportService;
    @MockBean
    private ReportWriter reportWriter;
}
```
 
 ### Use `ReflectionTestUtils` to modify private fields
 ```java
    //  mock data
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
 ### Use MockitoJUnitRunner to inject mocks
 ```java
@RunWith(MockitoJUnitRunner.class)
public class ReportServiceTest {
    @InjectMocks
    private ReportService reportService;
    @Mock
    private ReportWriter reportWriter;
}
 ```
 ### Use `@TestPropertySource` to inject test properties into private fields

```java
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
@TestPropertySource(properties = "report.global.name=" + REPORT_NAME)
public class ReportServiceTest {
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