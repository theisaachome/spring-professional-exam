package com.isaachome.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class BeforeAspect {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Before("execution(* com.isaachome.business.*.*(..))")
	public void before(JoinPoint joinPoint) {
		logger.info("Checking for user access");
		logger.info("Intercepted Method Calls - {}",joinPoint);
	}
}
