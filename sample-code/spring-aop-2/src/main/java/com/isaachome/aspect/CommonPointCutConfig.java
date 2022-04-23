package com.isaachome.aspect;
import org.aspectj.lang.annotation.Pointcut;

public class CommonPointCutConfig {

	@Pointcut("execution(* com.isaachome.data.*.*(..))")
	public void dataLayerExection() {}
	@Pointcut("execution(* com.isaachome.business.*.*(..))")
	public void businessLayerExection() {}
	
	@Pointcut("execution(* com.isaachome.data.*.*(..)) && execution(* com.isaachome.business.*.*(..))")
	public void allLayerExecution() {}
	
	@Pointcut("bean(*dao*)")
	public void beanContainingDao() {}
	@Pointcut("within(com.isaachome.data..*)")
	public void dataLayerExecutionWithWithin() {}
}
