# Spring Aspect Oriented Programming.

- [Question 001](#question-001)
- [Question 002](#question-002)
- [Question 003](#question-003)
- [Question 004](#question-004)
- [Question 005](#question-005)
- [Question 007](#question-007)
- [Question 008](#question-008)
- [Question 009](#question-009)
- 

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
---
### Question 007

Pointcut designator types supported by Spring AOP:

- `execution`
- `within`
- `args`
- `bean`
- `this`
- `target`
- `@annotation`
- `@args`
- `@within`
- `@target`

## Pointcut designator `execution` 
- matches method execution.

General Form:
```java
execution([visibility modifiers] [return type] [package].[class].[method]([arguments]) [throws exceptions]
```
```java
@Before("execution(public !int com..HelloBean.say*(String, *))")
public void executionExample() {}

@After("execution(void com..HelloChildBean.validateName(..) throws java.io.IOException)")
public void executionWithExceptionExample() {}
```
Description:
- [visibility modifiers]
  - `public/protected`  
   If omitted all are matched, can be used with negation.   
   For example `!protected`

- [return type]
  - `void`, `primitive` or `Object` type, cannot be omitted.
  - can be used with wildcard `*`
  - can be used with negation, for example `!int`


- `[package]`
  - `package` in which class is located.
  - It be omitted if class is located within same package as aspect.
  - wildcard * may be used to match all packages.
  - wildcard .. may be used to match all sub-packages.

- `[class]`
  - Class name to match against, 
  - may be omitted,
  - may be used with * wildcard, matches subclasses of the
  class as well.

- `[method]`
  - Name of the method,whole or partial method name can be used with * wildcard.

- `[arguments]`
  - May be empty to match methods without any arguments,
  - may be used with wildcard `..` to match zero or more arguments, 
  - may be used with wildcard `*` to match all types of specific argument, 
  - may be used with `!` Negation

- `[throws exceptions]`
  - Match method that throws exceptions from given list, 
  - can be used with negation !


## Pointcut designator `within`
- matches execution within specified class/classes, optionally you can specify class package.

### General Form:
```java
within([package].[class])
```
Description:
- [package]
  - package where class is located, 
  - may be used with `.. wildcard` (includes all sub-packages) or with `* wildcard`, 
  - may be omitted

- [class]
  - class against which match should happen, 
  - may be used with * wildcard

## Example
```java
@Before("within(com..HelloChildBean)")
public void withinExample1() {}

@Before("within(com..*)")
public void withinExample2() { }
```

## Pointcut designator  `args`
Matches execution of method with matching arguments 

### General Form:
```
args([parameter_type1, parameter_type2, ..., parameter_typeN])
```

### Example
```java
@Before("args(..)")
public void argsExample1() {}

@Before("args(String, int)")
public void argsExample2() {}

@Before("args(String, *)")
public void argsExample3() {}

@Before("args(java.lang.String)")
public void argsExample4() {}

```
### Description:
 `[parameter_typeN]`
 
 - Simple or object type, 
 - may be * to indicate one parameter of any type, 
 - may be .. to indicate zero or more arguments, 
 - you can specify type with the package.



## Pointcut designator – `bean`
- Matches execution of method with matching Spring Bean Name

###  General Form:
```
bean([beanName])
```
Description:
- [beanName]
- Name of the Spring Bean (automatically generated by framework, or set manually),

```java
@Before("bean(hello_child_bean)")
public void beanExample1() {}

@Before("bean(hello_*_bean)")
public void beanExample2() {}
```

## Pointcut designator – this
- Matches execution against type of proxy that was generated by Spring AOP
## General Form:
```
this([type])
```
Description:
- [type]
- type of the proxy, matches if generated proxy is of specified type
   

## Pointcut designator – `@annotation`
- Matches method execution annotated with specified annotation
### General Form:
```
@annotation([annotation_type])
```
Description:
- [annotation_type]
- type of annotation used to annotated method which should match pointcut expression
---
## Question 008 
## What is the JoinPoint argument used for?

`JoinPoint` argument is an object that can be used to retrieve additional information about join point during execution.

`JoinPoint` needs to be the first parameter of Advice, only in that case Spring Framework will inject `JoinPoint` into advice method.

Join Point is supported in following advice types:

- Before
- After
- After Returning
- After Throwing

Examples of information that you can retrieve from `JoinPoint`:
- String representation of `JoinPoint`
- Arguments of `JointPoint` (for example Method Arguments)
- Signature of `JointPoint` (for example Method Signature)
- Kind / Type of `JointPoint`
- Target / This object being proxied

---
## Question 009

## What is a ProceedingJoinPoint? When is it used?


ProceedingJoinPoint is an object that can be provided to @Around advice as first argument, it is a type of JoinPoint which can be used to change method arguments during method execution in runtime or block execution of original method entirely.


ProceedingJoinPoint is used in `@Around` advice, it contains all methods from `JoinPoint` and also adds:
- proceed – executes original method
- proceed(args) – executes original method with provided arguments

ProceedingJoinPoint can be used in following use cases: 
- Conditionally block method execution
- Filter arguments
- Inject additional argument