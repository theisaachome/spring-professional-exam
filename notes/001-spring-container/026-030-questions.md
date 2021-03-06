##

- [Question 26](#question-26)
- [Question 27](#question-27)
- [Question 28](#question-28)
- [Question 29](#question-29)
- [Question 30](#question-30)

---

## Question 26

## **Can you use @Component together with @Profile?**

Yes, @Profile annotation can be used together with @Component on top of class representing spring bean.

If, class annotated with @Component does not have @Profile, that beans that this bean will exists in all profiles.

You can specify one, multiple profiles, or profile in which bean should not exists:

```java
@Profile("database")
@Profile("!prod")
@Profile({"database", "file"})
```

### Example

```java
@Component
public class DataMapper {}
```

```java
@Component
@Profile({"database", "file"})
public class MultiSourceDataProcessor implements DataProcessor {}
```

```java
@Component
@Profile("database")
public class DbDataReader implements DataReader {}
```

```java
@Component
@Profile("file")
public class FileDataReader implements DataReader {}
```

```java
@Configuration
@ComponentScan
public class ApplicationConfiguration {}
```

```java
 // Activate profile
 context.getEnvironment().setActiveProfiles("database", "prod");
 context.register(ApplicationConfiguration.class);
 context.refresh();
```

---

## **Question 27**

## **How many profiles can you have?**

Spring Framework does not specify any explicit limit on number of profiles.

however since some of the classes in Framework, like `ActiveProfilesUtils` used by default implementation of `ActiveProfilesResolver` are using array to iterate over profiles.

this enforces inexplicit limit that is equal to maximum number of elements in array that you can have in Java, which is Integer.MAX_VALUE - 2,147,483,647 (231 − 1).

---

## Question 28

## How do you inject scalar/literal values into Spring beans?

To inject scalar/literal values into Spring Beans, you need to use `@Value` annotation.

`@Value` annotation has one field value that accepts:

- Simple value
- Property reference
- SpEL String

`@Value` annotation can be used on top of:

- Field

- Constructor Parameter

- Method

  - All fields will have injected the same value.

- Method parameter

  - Injection will not be performed automatically if `@Value` is not present on method level or if `@Autowired` is not present at method level.

- Annotation type

## Inside `@Value` you can specify:

- Simple value
  - `@Value("John"), @Value("true")`
  ```java
  @Value("John")
  private String name;
  @Value("true")
  private boolean accountExists;
  ```
- Reference a property

  - Configuration
    ```java
    @Configuration
    @ComponentScan
    @PropertySource("application.properties")
    public class ApplicationConfiguration {}
    ```
  - `@Value("${app.department.id}")`
    ```java
    @Value("${app.department.id}")
    private int departmentId;
    ```

- Perform SpEL inline computation
  - `@Value("#{'Wall Street'.toUpperCase()}")`
    ```java
    @Value("#{'Wall Street'.toUpperCase()}")
    private String streetName;
    ```
  - `@Value("#{5000 * 0.9}")`
    ```java
    @Value("#{5000 * 0.9}")
    private float accountBalance;
    ```
  - `@Value("#{'${app.department.id}'.toUpperCase()}") `
    ```java
    @Value("#{'${app.department.id}'.toUpperCase()}")
    private String departmentName;
    ```
- Inject values into array, list, set, map

  ```java
  @Value("${app.dependent.departments}")
  private String[] dependentDepartments;

  @Value("${app.cases.id}")
  private List<Integer> casesIds;

  @Value("${app.cases.set}")
  private Set<String> casesSet;

  @Value("#{${app.cases.map}}")
  private Map<String, Integer> casesMap;
  ```

---

## Question 29

## What is `@Value` used for?

`@Value` is used for:

- Setting simple values of Spring Bean Fields, Method Parameters, Constructor Parameters.

  ```java
  @Value("John")
  private String name;

  @Value("true")
  private boolean accountExists;

  @Value("${app.support.contact}")
  public void setSupportContactMail(String supportContactMail) {
      this.supportContactMail = supportContactMail;
  }

  ```

- Injecting property/environment values into Spring Bean Fields, Method Parameters,
  Constructor Parameters.
    ```java
    @Value("${app.department.id}")
    private int departmentId;
    ```

- Injecting results of SpEL expressions into Spring Bean Fields, Method Parameters, Constructor
  Parameters.

      ```java
      @Value("#{'Wall Street'.toUpperCase()}")
      private String streetName;
      ```

- Injecting values from other Spring Beans into Spring Bean Fields, Method Parameters,
  Constructor Parameters.

```java
@Component
public class SpringBean2 {
    @Value("3")
    private int taxId;
}
```
```java
@Component
public class SpringBean2 {
   @Value("#{springBean2.taxId}")
    private int taxId;
}
```
- Injecting values into collections (arrays, lists, sets, maps) from literals,
  property/environment values, other Spring Beans.

    ```java
    @Value("${app.dependent.departments}")
    private String[] dependentDepartments;

    @Value("${app.cases.id}")
    private List<Integer> casesIds;

    @Value("${app.cases.set}")
    private Set<String> casesSet;

    @Value("#{${app.cases.map}}")
    private Map<String, Integer> casesMap;

    ```
- Setting default values of Spring Bean Fields, Method Parameters, Constructor Parameters
  when referenced value is missing

  ```java
    // there is no value for alt.name in properties file
    // no_name value  will be used.
    @Value("${app.tax.department.alt.name:no_name}")
    private String taxDepartmentAlternateName;
  ```
