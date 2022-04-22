package com.isaachome.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class HelloServiceAspect {

//	@Before("execution(* com.isaachome.services.HelloService.hello(..))")
//	public void before() {
//		System.out.println("A");
//	}
//
//	@After("execution(* com.isaachome.services.HelloService.hello(..))")
//	public void after() {
//		System.out.println("B");
//	}
//
//	// work only if there is no exception
//	@AfterReturning("execution(* com.isaachome.services.HelloService.hello(..))")
//	public void afterReturning() {
//		System.out.println("C");
//	}
//
//	// work only if there is an exception
//	@AfterThrowing("execution(* com.isaachome.services.HelloService.hello(..))")
//	public void afterThrowing() {
//		System.out.println("D");
//	}
	@Around("execution(* com.isaachome.services.HelloService.hello(..))")
	public Object around(ProceedingJoinPoint joinPoint) {
//		System.out.println("Something else: ");
//		return "Something else:)";
		System.out.println("A");
		Object result = null;
		try {
			result = joinPoint.proceed();
			System.out.println("B");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return result;
	}
}
