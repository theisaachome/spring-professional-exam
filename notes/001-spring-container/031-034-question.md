


- [Question 31](#question-26)
- [Question 27](#question-27)
- [Question 28](#question-28)
- [Question 29](#question-29)
- [Question 34](#question-34)

---

## Question 31
## What is the Environment abstraction in Spring?

Environment Abstraction is part of Spring Container that models two key aspect of application environment: 
- Profiles
- Properties

Environment Abstraction is represent on code level by classes that implements Environment interface.

This interface allows you to resolve properties and also to list profiles.   

You can receive reference to class that implements Environment by calling EnvironmentCapable class, implemented by `ApplicationContext`.     

Properties can also be retrieved by using `@Value("${...}")` annotation.


Environment Abstraction role in context of profiles is to determine which profiles are currently active, and which are activated by default.

Environment Abstraction role in context of properties is to provide convenient, standarized and generic service that allows to resolve properties and also to configure property sources. 

Properties may come from following sources:
- PropertiesFiles
- JVMsystemproperties
- SystemEnvironmentVariables
- JNDI
- ServletConfig
- ServletContextParameters


Default property sources for standalone applications are configured in StandardEnvironment, which includes JVM system properties and System Environment Variables.

When running Spring Application in Servlet Environment, property sources will be configured based on StandardServletEnvironment, which additionally includes Servlet Config and Servlet Context Parameters, optionally it might include JndiPropertySource.

Too add additional properties files as property sources you can use `@PropertySource` annotation.

---

## Question 32 
## Where can properties in the environment come from?

Property Sources for Standalone Spring Framework Application: 
- Properties Files
- JVM system properties
- System Environment Variables


Property Sources for Servlet Container Spring Framework Application: 
- Properties Files
- JVM system properties
- System Environment Variables
- JNDI
- ServletConfig init parameters 
- ServletContext init parameters


Property Sources for Spring Boot Application:

- Devtools properties from ~/.spring-boot-devtools.properties (when devtools is active) 
- `@TestPropertySource` annotations on tests
- Properties attribute in `@SpringBootTest` tests
- Command line arguments
- Properties from SPRING_APPLICATION_JSON property
- `ServletConfig` init parameters
- `ServletContext` init parameters
- JNDI attributes from java:comp/env
- JVM system properties
- System Environment Variables
- `RandomValuePropertySource` - ${random.*}
- application-{profile}.properties and YAML variants - outside of jar
- application-{profile}.properties and YAML variants – inside jar
- application.properties and YAML variants - outside of jar
- application.properties and YAML variants - inside jar
- `@PropertySource` annotations on `@Configuration` classes
- Default properties - `SpringApplication.setDefaultProperties`

---


## Question 34 
## What can you referenc using SpEL?

You can reference following using SpEL:
- Static field from class
    - `T (com.example.Person).DEFAULT_NAME`
- Static methods from class
    - `T (com.example.Person).getDefaultName()`
- Spring bean Property
    - `@person.name`
- Spring bean Method 
    - `@person.getName()`
- SpEL Variables 
    - `#personName`
- 
---

## Question 34 
## What is the difference between $ and # in @Value expressions?

`@Value` annotation supports two types of expressions:
Expressions starting with `$`
- Used to reference a property in Spring Environment Abstraction.

Expressions starting with `#` 
- SpEL expressions parsed and evaluated by `SpEL`