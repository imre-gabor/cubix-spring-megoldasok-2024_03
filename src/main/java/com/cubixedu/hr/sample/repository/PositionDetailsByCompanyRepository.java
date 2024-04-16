package com.cubixedu.hr.sample.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cubixedu.hr.sample.model.Position;
import com.cubixedu.hr.sample.model.PositionDetailsByCompany;

public interface PositionDetailsByCompanyRepository extends JpaRepository<PositionDetailsByCompany, Long> {

	List<PositionDetailsByCompany> findByPositionNameAndCompanyId(String positionName, long companyId);

}
