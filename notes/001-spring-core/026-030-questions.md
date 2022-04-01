##

- [Question 26](#question-21)
- [Question 27](#question-22)
- [Question 28](#question-23)
- [Question 29](#question-24)
- [Question 30](#question-25)


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

Spring Framework does not specify any explicit limit on number of profiles, however since some of the classes in Framework, like ActiveProfilesUtils used by default implementation of ActiveProfilesResolver are using array to iterate over profiles, this enforces inexplicit limit that is equal to maximum number of elements in array that you can have in Java, which is Integer.MAX_VALUE - 2,147,483,647 (231 − 1).