package com.isaachome.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isaachome.data.EmployeeRepo;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepo employeeRepo;
	
	public String caculateSomething() {
//		business logic
		return employeeRepo.retrieveSomething();
	}
	
}
