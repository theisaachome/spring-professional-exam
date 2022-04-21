package com.isaachome.services;

import org.springframework.stereotype.Service;

@Service
public class HelloService {
	public void hello(String name) {
		System.out.println( "Hello, " + name + "!");
	}
}
