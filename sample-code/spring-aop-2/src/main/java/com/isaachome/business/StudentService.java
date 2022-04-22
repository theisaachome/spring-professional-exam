package com.isaachome.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isaachome.data.StudentRepo;

@Service
public class StudentService {

	@Autowired
	private StudentRepo studentRepo;
	
	public String caculateSomething() {
//		business logic
		return studentRepo.retrieveSomething();
	}
	
}
