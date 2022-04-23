package com.isaachome.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class AfterAOPAspect {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@AfterReturning(
			value="com.isaachome.aspect.CommonPointCutConfig.businessLayerExection()",
			returning="result")
	public void afterReturning(JoinPoint joinPoint,Object result) {
		logger.info("{} return with value {}",joinPoint,result);
	}
	@After(value="com.isaachome.aspect.CommonPointCutConfig.businessLayerExection()")
	public void after(JoinPoint joinPoint) {
		logger.info("after execution of {}",joinPoint);
	}
}
