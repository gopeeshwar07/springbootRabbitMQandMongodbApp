package com.spring.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.spring.pojo.Emp;

public interface EmpRepo extends MongoRepository<Emp, Integer> {

}
