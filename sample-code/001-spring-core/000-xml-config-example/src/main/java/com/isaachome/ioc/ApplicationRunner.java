package com.isaachome.ioc;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.isaachome.HelloWorld;

public class ApplicationRunner {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		 HelloWorld obj = (HelloWorld) context.getBean("helloWorld");
	        obj.getMessage();
	        
	}
}
