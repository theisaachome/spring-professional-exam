package com.isaachome;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.isaachome.config.ProjectConfig;
import com.isaachome.services.HelloService;

public class RunnerApp {
	public static void main(String[] args) {
		try(var context = new AnnotationConfigApplicationContext(ProjectConfig.class)) {
			HelloService helloService = context.getBean(HelloService.class);
			helloService.hello("John");
//			var result =
//			System.out.println(result);
		}
	}
}
