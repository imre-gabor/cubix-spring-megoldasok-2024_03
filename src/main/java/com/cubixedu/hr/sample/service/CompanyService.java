package com.cubixedu.hr.sample.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cubixedu.hr.sample.model.Company;
import com.cubixedu.hr.sample.model.Employee;
import com.cubixedu.hr.sample.repository.CompanyRepository;
import com.cubixedu.hr.sample.repository.EmployeeRepository;

@Service
public class CompanyService {

	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Transactional
	public Company save(Company company) {
		return companyRepository.save(company);
	}
	
	@Transactional
	public Company update(Company company) {
		if(!companyRepository.existsById(company.getId()))
			return null;
		return companyRepository.save(company);
	}

	public List<Company> findAll(boolean full) {
		return full ? 
				companyRepository.findAllWithEmployees()
				: companyRepository.findAll();
	}

	public Optional<Company> findById(long id, boolean full) {
		return full ?
				companyRepository.findByIdWithEmployees(id)
				: companyRepository.findById(id);
	}

	@Transactional
	public void delete(long id) {
		companyRepository.deleteById(id);
	}
	
	
	@Transactional
	public Company addEmployee(long id, Employee employee) {
		Company company = companyRepository.findByIdWithEmployees(id).get();
		company.addEmployee(employeeService.save(employee));
		return company;
	}
	
	@Transactional
	public Company deleteEmployee(long id, long employeeId) {
		Company company = companyRepository.findByIdWithEmployees(id).get();
		Employee employee = employeeRepository.findById(employeeId).get();
		employee.setCompany(null);
		company.getEmployees().remove(employee);
		//employeeRepository.save(employee); @Transactional miatt nem kell, az employee menedzselt példány
		return company;
	}
	
	@Transactional
	public Company replaceEmployees(long id, List<Employee> employees) {
		Company company = companyRepository.findByIdWithEmployees(id).get();
		company.getEmployees().forEach(e -> e.setCompany(null));
		company.getEmployees().clear();
		
		employees.forEach(e -> {
			company.addEmployee(employeeService.save(e));			
		});
		
		return company;
	}
}
