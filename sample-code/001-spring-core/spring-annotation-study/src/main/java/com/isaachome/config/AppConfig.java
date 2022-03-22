package com.isaachome.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.isaachome.beans.Cat;
import com.isaachome.beans.Owner;

@Configuration
@ComponentScan(basePackages = "com.isaachome.beans")
public class AppConfig {

//	@Bean
//	public Cat cat() {
//		Cat c = new Cat();
//		c.setName("Tom");
//		return c;
//	}
//	
//	@Bean
//	public Owner owner() {
//		var owner = new Owner();
//		// calling the method of the bean which
//		// return the object.
//		owner.setCat(cat());
//		return owner;
//	}
	// using method arguments
//	@Bean
//	public Owner owner(Cat cat) {
//		var owner = new Owner();
//		owner.setCat(cat);
//		return owner;
//	}
}
