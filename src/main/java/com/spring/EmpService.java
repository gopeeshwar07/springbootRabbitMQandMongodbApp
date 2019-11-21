package com.spring;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.pojo.Emp;
import com.spring.repo.EmpRepo;

@Service
public class EmpService {
	
	@Autowired
	EmpRepo empRepo;

	public List<Emp> getEmp() {
		// TODO Auto-generated method stub
		return empRepo.findAll();
	}
	
	public Emp saveEmp(Emp e) {
		return empRepo.save(e);
	}
	

}
