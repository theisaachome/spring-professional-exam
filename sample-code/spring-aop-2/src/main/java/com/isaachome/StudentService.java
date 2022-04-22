package com.isaachome;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

	@Autowired
	private EmployeeRepo repo;
	
	public String caculateSomething() {
//		business logic
		return repo.retrieveSomething();
	}
	
}
