# Spring Aspect Oriented Programming.

- [Question 001](#question-001)
- [Question 002](#question-002)
- [Question 003](#question-003)
- [Question 004](#question-004)
- [Question 005](#question-005)

---

## Question 001

## What is the Concept of AOP?

### AOP `Aspect Oriented Programming`

A programming paradigm that complements Object-oriented Programming (OOP) by providing a way to separate groups of cross-cutting concerns from business logic code.  
This is achieved by ability to add additional behavior to the code without having to modify the code itself.

This is achieved by specifying:

- Location of the code which behavior should be altered – Pointcut is matched with Join point.

- Code which should be executed that implements cross cutting concern – Advice

## Which problem does it solve?Which problem does it solve?

Aspect Oriented Programming solves following challenges:

- Allows proper implementation of Cross-Cutting Concerns.

- Solves Code Duplications by eliminating the need to repeat the code for functionalities across different layers, such functionalities may include logging, performance logging, monitoring, transactions, caching.

- Avoids mixing unrelated code, for example mixing transaction logic code (commit, rollback) with business code makes code harder to read, by separating concerns code is easier to read, interpret, maintain

## Name three typical cross cutting concerns.

Common cross-cutting concerns:

- Logging
- Performance Logging
- Caching
- Security
- Transactions
- Monitoring

## What two problems arise if you don't solve a cross cutting concern via AOP?

Implementing cross-cutting concerns without using AOP, produces following challenges:

- Code duplications – Before/After code duplicated in all locations when normally Advise would be applied, refactoring by extraction helps but does not fully solve the problem

- Mixing of concerns – business logic code mixed with logging, transactions, caching makes code hard read and maintain

---

## Question 002

## What is a Join Point

`Join Point` in aspect oriented programming is a point in execution of a program in which behavior can be altered by AOP.

In Spring AOP `Join Point` is always method execution.

```java
public interface CurrencyService{
    // Join Point
    float getExchangeRate(String from, String to);
    // Join Point
    float getExchangeRate(String from, String to, int multiplier);
    // Join Point
    String getCurrencyLongName(CurrencyId currencyId);
    // Join Point
    String getCurrencyCountryName(CurrencyId currencyId);
}
```

Aspect Oriented Programming concept in general, distinguishes additional Join Points, some of them include:

- Method Execution / Invocation
- Constructor Execution / Invocation
- Reference / Assignment to Field
- Exception Handler
- Execution of Advice
- Execution of Static Initializer / Object Initializer

---

## What is a pointcut?

`Pointcut` is a predicate used to match join point. Additional code, called Advice is executed in all parts of the program that are matching pointcut. Spring uses the AspectJ `pointcut` expression language by default.

Example of Pointcut Expressions:

- `execution` - Match Method Execution
  ```
  execution(* com.isaachome.bls.CurrencyService.getExchangeRate(..))
  ```
- `within` - Match Execution of given type or types inside package
  ```
  within(com.isaachome.bls.*)
  ```
- `@within` – Match Execution of type annotated with annotation
  ```
  @within(com.isaachome.annotations.Secured)
  ```
- `@annotation` – Match join points where the subject of the join point has the given annotation

  ```
  @annotation(com.isaachome.annotations.InTransaction)
  ```

- `bean` – Match by spring bean name
  ```
  bean(currency_service)
  ```
- `args` – Match by method arguments
  ```
  args(String, String, int)
  ```
- `@args` – Match by runtime type of the method arguments that have annotations of the given type
  ```
  @args(com.isaachome.annotations.Validated)
  ```
- `this` – Match by bean reference being an instance of the given type (for CGLIB-based proxy)
  ```
  this(com.isaachome.bls.CurrencyService)
  ```
- `target` – Match by target object being an instance of the given type
  ```
  target(com.isaachome.bls.CurrencyService)
  ```
- `@target` – Match by class of the executing object having an annotation of the given type
  ```
  @target(com.isaachome.annotations.Secured)
  ```

---

## What is an advice

Advice is additional behavior that will be inserted into the code, at each join point matched by pointcut.

```java
// define PointCut
@Pointcut("@annotation(com.isaachome.annotations.InTransaction)") public void transactionAnnotationPointcut() {}

// using point cut
@Before("transactionAnnotationPointcut()")
public void beforeTransactionAnnotationAdvice(){
// Advice
System.out.println("Before - transactionAnnotationPointcut");
}

```

### **Inline Pointcut & Advice**

```java
@Before("this(com.isaachome.bls.CurrenciesRepositoryImpl)") public void beforeThisCurrenciesRepository() {
System.out.println("Before - this(CurrenciesRepositoryImpl)"); }
```

## What is Aspect ?

Aspect brings together Pointcut and Advice. Usually it represents single behavior implemented by advice that will be added to all join points matched by pointcut.

```java
// Aspect
@Component
@Aspect
public class CurrenciesRepositoryAspect {
// Pointcut 
@Before("this(com.isaachome.bls.CurrenciesRepositoryImpl)") 
public void beforeThisCurrenciesRepository() {
// Advice
System.out.println("Before - this(CurrenciesRepositoryImpl)"); }
}

```

---

## What is Weaving?

Weaving is the process of applying aspects, which modifies code behavior at join points that have matching pointcuts and associated advices. During weaving aspects and application code is combined which enables execution of cross-cutting concerns.

Types of weaving:
- `Compile Time Weaving` – byte code is modified during the compilation, aspects are applied, code is modified at join points matching pointcuts by applying advices
- `Load Time Weaving` – byte code is modified when classes are loaded by class loaders, during class loading aspects are applied, code is modified at join points matching pointcuts by applying advices
- `Runtime Weaving` – used by Spring AOP, for each object/bean subject to aspects, proxy object is created (JDK Proxy or CGLIB Proxy), proxy objects are used instead of original object, at each join point matching pointcut, method invocation is changed to apply code from advice

---

## Question 3
## How does Spring solve(implement) a cross cutting concern?
Spring Implements cross-cutting concerns with usage of Spring AOP module. Spring AOP uses AspectJ expression syntax for Pointcut expressions, which are matched against Join Point, code is altered with logic implemented in advices. In Spring AOP Joint Point is always method invocation.

Spring AOP uses Runtime Weaving, and for each type subject to aspects, to intercepts calls, spring creates one type of proxy:

- JDK Proxy – created for classes that implements interface

![](./jdk-proxy.png)
- CGLIB Proxy – created for class that are not implementing any interface
![](./cglib.png)

It is possible to force Spring to use CGLIB Proxy with usage of
```java
@EnableAspectJAutoProxy(proxyTargetClass = true)
```