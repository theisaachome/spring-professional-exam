package com.isaachome.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Computer {

	
	@Autowired
	private Motherbroad motherbroad;
	@Autowired
	private CPU cpu;
	public void run() {
		motherbroad.start();
		cpu.show();
	}
}
