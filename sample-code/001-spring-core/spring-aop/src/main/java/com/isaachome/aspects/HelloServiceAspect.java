package com.isaachome.aspects;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class HelloServiceAspect {

	@Before("execution(* com.isaachome.services.HelloService.hello(..))")
	public void before() {
		System.out.println("Before HelloService");
	}
	
	@After("execution(* com.isaachome.services.HelloService.hello(..))")
	public void after() {
		System.out.println("After HelloService");
	}
	//work only if there is no exception
	@AfterReturning("execution(* com.isaachome.services.HelloService.hello(..))")
	public void afterReturning() {
		System.out.println("AfterReturninng HelloService");
	}
	
	//work only if there is an exception
		@AfterThrowing("execution(* com.isaachome.services.HelloService.hello(..))")
		public void afterThrowing() {
			System.out.println("AfterThrowing HelloService");
		}
}
