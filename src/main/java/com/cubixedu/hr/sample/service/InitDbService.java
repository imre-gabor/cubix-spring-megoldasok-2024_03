package com.cubixedu.hr.sample.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cubixedu.hr.sample.model.Company;
import com.cubixedu.hr.sample.model.Employee;
import com.cubixedu.hr.sample.model.Position;
import com.cubixedu.hr.sample.model.PositionDetailsByCompany;
import com.cubixedu.hr.sample.model.Qualification;
import com.cubixedu.hr.sample.repository.CompanyRepository;
import com.cubixedu.hr.sample.repository.EmployeeRepository;
import com.cubixedu.hr.sample.repository.HolidayRequestRepository;
import com.cubixedu.hr.sample.repository.PositionDetailsByCompanyRepository;
import com.cubixedu.hr.sample.repository.PositionRepository;

@Service
public class InitDbService {

	@Autowired
	PositionRepository positionRepository;
	
	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	CompanyRepository companyRepository;
	
	@Autowired
	PositionDetailsByCompanyRepository positionDetailsByCompanyRepository;
	
	@Autowired
	HolidayRequestRepository holidayRequestRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public void clearDb() {
		positionDetailsByCompanyRepository.deleteAllInBatch();
		holidayRequestRepository.deleteAllInBatch();
		employeeRepository.deleteAllInBatch();
		positionRepository.deleteAllInBatch();
		companyRepository.deleteAllInBatch();
	}
	
	@Transactional
	public void initDb() {
		
		Position developer = positionRepository.save(new Position("fejlesztő", Qualification.UNIVERSITY));
		Position tester = positionRepository.save(new Position("tesztelő", Qualification.HIGH_SCHOOL));
		
		Employee newEmployee1 = employeeRepository.save(new Employee(null, "ssdf", developer, 200000, LocalDateTime.now()));
		newEmployee1.setPassword(passwordEncoder.encode("pass"));
		newEmployee1.setUsername("user1");
		Employee newEmployee2 = employeeRepository.save(new Employee(null, "t35", tester, 200000, LocalDateTime.now()));
		newEmployee2.setPassword(passwordEncoder.encode("pass"));
		newEmployee2.setUsername("user2");
		newEmployee1.setManager(newEmployee2);
		Company newCompany = companyRepository.save(new Company(null, 10, "sdfsd", "", null));
		newCompany.addEmployee(newEmployee2);
		newCompany.addEmployee(newEmployee1);
		
		PositionDetailsByCompany pd = new PositionDetailsByCompany();
		pd.setCompany(newCompany);
		pd.setMinSalary(250000);
		pd.setPosition(developer);
		positionDetailsByCompanyRepository.save(pd);
		
		PositionDetailsByCompany pd2 = new PositionDetailsByCompany();
		pd2.setCompany(newCompany);
		pd2.setMinSalary(200000);
		pd2.setPosition(tester);
		positionDetailsByCompanyRepository.save(pd2);
	}
}
