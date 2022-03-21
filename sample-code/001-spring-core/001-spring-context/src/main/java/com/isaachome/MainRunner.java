package com.isaachome;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.isaachome.beans.MyBean;
import com.isaachome.beans.Student;

public class MainRunner {
	// XML
	// Annotation
	public static void main(String[] args) {
		try(var context =
			new AnnotationConfigApplicationContext(ProjectConfig.class)) {
			//context.refresh();
			MyBean b1=context.getBean(MyBean.class);
			System.out.println(b1.getMessage());
			
			/*
				get bean by using name 
				if there is no name setup 
				the method name is used.
			*/
			
			/*
				example using 
				the method name.
			 */
			Student s = context.getBean("student",Student.class);
			System.out.println(s.getName());
			/*
			example using 
			the bean name.
			*/
			var s2 = context.getBean("fresh-student",Student.class);
			System.out.println(s2.getName());
		} 
	}
}
