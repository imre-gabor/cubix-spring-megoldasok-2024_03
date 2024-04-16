package com.cubixedu.hr.sample.service;

import org.springframework.stereotype.Service;

import com.cubixedu.hr.sample.model.Employee;
import com.cubixedu.hr.sample.repository.EmployeeRepository;
import com.cubixedu.hr.sample.repository.PositionDetailsByCompanyRepository;
import com.cubixedu.hr.sample.repository.PositionRepository;

@Service
public class SalaryService {

	private EmployeeService employeeService;
	private PositionRepository positionRepository;
	private PositionDetailsByCompanyRepository positionDetailsByCompanyRepository;
	private EmployeeRepository employeeRepository;
	
	public SalaryService(EmployeeService employeeService, PositionRepository positionRepository,
			PositionDetailsByCompanyRepository positionDetailsByCompanyRepository,
			EmployeeRepository employeeRepository) {
		this.employeeService = employeeService;
	}

	public void setNewSalary(Employee employee) {
		int newSalary = employee.getSalary() * (100 + employeeService.getPayRaisePercent(employee)) / 100;
		employee.setSalary(newSalary);
	}

}
