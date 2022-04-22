package com.isaachome;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.isaachome.business.EmployeeService;
import com.isaachome.business.StudentService;

@SpringBootApplication
public class SpringAop2Application implements CommandLineRunner {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private StudentService studentService;

	public static void main(String[] args) {
		SpringApplication.run(SpringAop2Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info(employeeService.caculateSomething());
		 logger.info(studentService.caculateSomething());
	}

}
