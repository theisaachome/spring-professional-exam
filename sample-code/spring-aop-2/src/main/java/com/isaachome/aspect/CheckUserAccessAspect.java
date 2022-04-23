package com.isaachome.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

//  Aspect
@Aspect
@Configuration
public class CheckUserAccessAspect {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
/*
	Aspect is 
*/
	//PointCut
	@Before("com.isaachome.aspect.CommonPointCutConfig.dataLayerExection()")
	public void before(JoinPoint joinPoint) {
	//Advice
		logger.info("Checking for user access");
		logger.info("Intercepted Method Calls - {}",joinPoint);
	}
}
