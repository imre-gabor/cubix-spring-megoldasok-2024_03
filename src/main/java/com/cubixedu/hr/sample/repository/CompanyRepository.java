package com.cubixedu.hr.sample.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cubixedu.hr.sample.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Long>{

}
