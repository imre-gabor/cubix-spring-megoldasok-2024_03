package com.cubixedu.hr.sample.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		this.positionRepository = positionRepository;
		this.positionDetailsByCompanyRepository = positionDetailsByCompanyRepository;
		this.employeeRepository = employeeRepository;
	}


	public void setNewSalary(Employee employee) {
		int newSalary = employee.getSalary() * (100 + employeeService.getPayRaisePercent(employee)) / 100;
		employee.setSalary(newSalary);
	}
	
	
	@Transactional
	public void raiseMinSalary(long companyId, String positionName, int minSalary) {
		
		positionDetailsByCompanyRepository.findByPositionNameAndCompanyId(positionName, companyId)
			.forEach(pd -> {
				pd.setMinSalary(minSalary);
				//1. megoldás: employee-k betöltése + egyesével UPDATE-elése nem hatékony
//				pd.getCompany().getEmployees().forEach(e ->{
//					if(e.getPosition().getName().equals(positionName) && e.getSalary() < minSalary)
//						e.setSalary(minSalary);
//				});
				
				//2. megoldás
				employeeRepository.updateSalaries(companyId, positionName, minSalary);
			});
		
	}

}
