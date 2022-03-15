package com.isaachome;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.isaachome.domain.Computer;

public class AppRunner {
	public static void main(String[] args) {
		try(AnnotationConfigApplicationContext context =
				new AnnotationConfigApplicationContext(ApplicationConfiguration.class)){
			Computer computer = context.getBean(Computer.class);
			computer.run();
			
		}
	}
}
