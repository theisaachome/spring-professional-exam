# **Spring Professional Exam**

## **Dependency Container and IoC**

- ### [Table of Contents](#table-of-contents)
- [Question 01](#question-01)
- [Question 02](#question-02)
- [Question 03](#question-03)
- [Question 04](#question-04)
- [Question 05](#question-05)

---

### **Question 01**

### What is dependency injection?

- The Inversion of Control (IoC) principle, `IoC` is also known as dependency injection (DI).

- It is a process whereby objects define their dependencies.

- An object or framework provide concrete
    dependencies to the objects.

**Types of Dependency Injection:**
  - Constructor injection
  - Setter injection
  - Interface injection

### **what are the advantages?**

**Advantages of using dependency injection is:**
  - Increases code reusability
  - Increases code readability
  - Increases code maintainability
  - Increases code testability
  - Reduces coupling
  - Increases cohesion

---

### **Question 02**

### What is a pattern? What is an anti-pattern?

  - A reusable solution to often, commonly occurring problem in software design.

  - It is a high level description on how to solve the problem

  - Design patterns often represent best practices that developers can use to solve common problems.

- Commonly used design patterns from GoF Design Patterns:
  - FactoryMethod
  - Builder
  - TemplateMethod
  - Strategy
  - Observer
  - Visitor
  - Facade
  - Composite

### Is dependency injection a pattern?

- Dependency Injection is a pattern that solves problem of flexible dependencies creation.

- Anti-pattern is ineffective and counter-productive solution to often occurring problem.
  Examples of Anti-patterns in Object Oriented Programming:
  - God Object
  - Sequential coupling
  - Circular dependency
  - Constant interface

---

### **Question 03**

- ### What is an interface ?

- OOP Definition

  - A description of actions that object can do, it is a way to enforce actions on object that implements the interface.

- Java Definition

  - A reference type, which contains collections of abstract method.

  - Class that implements the interface, must implement all methods from this interface,

  - or it needs to declare methods as abstract

- Java Interface may contain also:

  - Constants
  - Default Methods (Java 8)
  - Static methods
  - Nested types

## What are the advantages of making use of them in Java? Why are they recommended for Spring beans?

- Advantages of using interfaces in Java:
  - Allows decoupling between contract and its implementation
  - Allows declaring contract between callee and caller
  - Increases interchangeability
  - Increases testability
- Advantages of using interfaces in Spring:
  - Allows for use of JDK Dynamic Proxy
  - Allows implementation hiding
  - Allows to easily switch beans

---

## **Question 04**

### What is meant by **`ApplicationContext`**?

### `ApplicationContext`:

- is a central part of Spring application.
- represents the Spring IoC container and
- is responsible for instantiating, configuring, and assembling the beans.
- It uses dependency injection to achieve inversion of control.


**`ApplicationContext`**
  - Initiates Beans
  - Configures Beans
  - Assemblies Beans
  - Manages Beans Lifecycle
  - is a BeanFactory
  - is a Resource Loader
  - Has ability to push events to registered even listeners
  - Exposes Environment which allows to resolve properties

### **Common ApplicationContext types:**
  - AnnotationConfigApplicationContext
  - AnnotationConfigWebApplicationContext
  - ClassPathXmlApplicationContext
  - FileSystemXmlApplicationContext
  - XmlWebApplicationContext

---


### **Question 05**

### What is the concept of a **`Container`**?

  - An execution environment which provides additional technical services for your code to use. 
  - Containers use IoC technique,
  that allows you to focus on creating business aspect of the code,
  - while technical aspects like communication details (HTTP, REST, SOAP) are provided by execution environment.


- Spring provides a container for beans.

- It manages lifecycle of the beans and 

- provides additional services through usage of `ApplicationContext`.

- ### **What is its lifecycle?**

- Spring Container Lifecycle:
  - Application is started.
  - Spring container is created.
  - Containers reads configuration  metadata;.
  - Beans definitions are created from configuration.
  - BeanFactoryPostProcessors are processing bean definitions.
  - Instances of Spring Beans are created.
  - Spring Beans are configured and assembled
  - resolve property values and inject dependencies.
  - BeanPostProcessors are called.
  - Application Runs.
  - Application gets shutdown.
  - Spring Context is closed.
  - Destruction callbacks are invoked.


----
