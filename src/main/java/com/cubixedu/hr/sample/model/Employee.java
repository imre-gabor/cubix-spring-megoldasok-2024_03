package com.cubixedu.hr.sample.model;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Employee {

	@Id
	@GeneratedValue
	private Long employeeId;
	private String name;
	private String jobTitle;
	private int salary;
	private LocalDateTime dateOfStartWork;
	
	@ManyToOne
	private Company company;
	
	public Employee() {
	}

	public Employee(Long employeeId, String name, String jobTitle, int salary, LocalDateTime dateOfStartWork) {
		this.employeeId = employeeId;
		this.name = name;
		this.jobTitle = jobTitle;
		this.salary = salary;
		this.dateOfStartWork = dateOfStartWork;
	}

	public Employee(int salary, LocalDateTime dateOfStartWork) {
		this.salary = salary;
		this.dateOfStartWork = dateOfStartWork;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public LocalDateTime getDateOfStartWork() {
		return dateOfStartWork;
	}

	public void setDateOfStartWork(LocalDateTime dateOfStartWork) {
		this.dateOfStartWork = dateOfStartWork;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@Override
	public int hashCode() {
		return Objects.hash(employeeId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		return Objects.equals(employeeId, other.employeeId);
	}
	
	
}
