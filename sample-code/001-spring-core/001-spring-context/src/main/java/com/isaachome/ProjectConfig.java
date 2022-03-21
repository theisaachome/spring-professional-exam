package com.isaachome;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.isaachome.beans.MyBean;
import com.isaachome.beans.Student;

@Configuration
public class ProjectConfig {

	//	create bean from POJO
	@Bean
	//	@Primary
	public MyBean myBean() {
		MyBean b = new MyBean();
		b.setMessage("Hello ");
		//return new MyBean();
		return b;
	}
	
	//	Use primary for default when there are many beans
	@Bean
	@Primary
	public MyBean myOtherBean() {
		var b = new MyBean();
		b.setMessage("World from otherBean.");
		return b;
	}
	
	@Bean()
	public Student student() {
		var s = new Student();
		s.setName("Aung Aung");
		return s;
	}
	@Bean("fresh-student")
	public Student student2() {
		var s = new Student();
		s.setName("Naw San Myint");
		return s;
	}
}
