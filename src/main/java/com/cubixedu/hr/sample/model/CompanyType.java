package com.cubixedu.hr.sample.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

//public enum CompanyType {
//	BT, KFT, ZRT, NYRT;
//}

@Entity
public class CompanyType {
	
	@Id
	@GeneratedValue
	private int id;
	
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}