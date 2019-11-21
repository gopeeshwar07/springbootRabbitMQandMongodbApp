package com.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.EmpService;
import com.spring.pojo.Emp;

@RestController
public class EmpController {
	
	@Autowired
	EmpService empService;
	@GetMapping("/emp")
	public List<Emp> getEmp() {
		return empService.getEmp();
	}

	@PostMapping("/save")
	public Emp saveEmp() {
		Emp e=new Emp(101,"gp",10000);
		return empService.saveEmp(e);
	}
}
