package com.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.spring.repo.EmpRepo;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = EmpRepo.class)
public class DemoApplication {

	public static void main(String[] args) {
		System.out.println("spring");
		SpringApplication.run(DemoApplication.class, args);
	}

}
