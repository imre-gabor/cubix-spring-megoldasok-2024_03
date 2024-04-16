package com.cubixedu.hr.sample.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.cubixedu.hr.sample.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{

	Page<Employee> findBySalaryGreaterThan(int minSalary, Pageable pageable);

	List<Employee> findByPositionName(String title);

	List<Employee> findByNameStartingWithIgnoreCase(String name);

	List<Employee> findByDateOfStartWorkBetween(LocalDateTime start, LocalDateTime end);

	
	@Modifying
	@Query("UPDATE Employee e "
			+ "SET e.salary = :minSalary "
			+ "WHERE e.position.name=:positionName "
			+ "AND e.company.id=:companyId "
			+ "AND e.salary < :minSalary")
	void updateSalaries(long companyId, String positionName, int minSalary);
	
}
