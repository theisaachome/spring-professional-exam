package com.isaachome;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.isaachome.beans.Cat;
import com.isaachome.beans.Owner;
import com.isaachome.config.AppConfig;

public class AppRunner {
	public static void main(String[] args) {
		try(var ctx=
				new AnnotationConfigApplicationContext(AppConfig.class)) {
			Cat c = ctx.getBean(Cat.class);
			Owner o = ctx.getBean(Owner.class);
			System.out.println(c);
			System.out.println(o);
		} 
	}
}
