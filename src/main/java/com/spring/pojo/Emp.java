package com.spring.pojo;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "emp")
public class Emp {
	@Id
	public int eid;
	public String ename;
	public double sal;
	public Emp(int eid, String ename, double sal) {
		// TODO Auto-generated constructor stub
		this.eid=eid;
		this.ename=ename;
		this.sal=sal;
	}
	public int getEid() {
		return eid;
	}
	public void setEid(int eid) {
		this.eid = eid;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public double getSal() {
		return sal;
	}
	public void setSal(double sal) {
		this.sal = sal;
	}
	
	

}
