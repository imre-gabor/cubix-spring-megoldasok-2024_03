package com.cubixedu.hr.sample.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cubixedu.hr.sample.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{

	List<Employee> findBySalaryGreaterThan(int minSalary);

	List<Employee> findByJobTitle(String title);

	List<Employee> findByNameStartingWithIgnoreCase(String name);

	List<Employee> findByDateOfStartWorkBetween(LocalDateTime start, LocalDateTime end);
	
}
