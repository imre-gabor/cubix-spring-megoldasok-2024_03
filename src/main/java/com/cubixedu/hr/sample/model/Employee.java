package com.cubixedu.hr.sample.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Employee {

	@Id
	@GeneratedValue
	private Long employeeId;
	private String name;
	private int salary;
	private LocalDateTime dateOfStartWork;
	
	private String username;
	private String password; 
	
	@ManyToOne
	private Company company;
	
	@ManyToOne
	private Position position;


	@OneToMany(mappedBy = "employee")
	private List<HolidayRequest> holidayRequests;

	@ManyToOne
	private Employee manager;
	
	@OneToMany(mappedBy = "manager")
	private List<Employee> managedEmployees;
	
	public Employee() {
	}

	public Employee(Long employeeId, String name, Position position, int salary, LocalDateTime dateOfStartWork) {
		this.employeeId = employeeId;
		this.name = name;
		this.position = position;
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

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}
	public List<HolidayRequest> getHolidayRequests() {
		return holidayRequests;
	}

	public void setHolidayRequests(List<HolidayRequest> holidayRequests) {
		this.holidayRequests = holidayRequests;
	}

	public void addHolidayRequest(HolidayRequest holidayRequest) {
		if (this.holidayRequests == null)
			this.holidayRequests = new ArrayList<>();

		this.holidayRequests.add(holidayRequest);
		holidayRequest.setEmployee(this);
	}

	public Employee getManager() {
		return manager;
	}

	public void setManager(Employee manager) {
		this.manager = manager;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Employee> getManagedEmployees() {
		return managedEmployees;
	}

	public void setManagedEmployees(List<Employee> managedEmployees) {
		this.managedEmployees = managedEmployees;
	}

}
