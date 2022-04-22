package com.isaachome;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.isaachome.config.ProjectConfig;
import com.isaachome.services.HelloService;

public class RunnerApp {
	public static void main(String[] args) {
		try(var context = new AnnotationConfigApplicationContext(ProjectConfig.class)) {
			HelloService helloService = context.getBean(HelloService.class);
			
			String message =helloService.hello("John");
			System.out.println("Result is " + message);
		}
	}
}
