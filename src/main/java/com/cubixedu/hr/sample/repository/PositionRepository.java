package com.cubixedu.hr.sample.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cubixedu.hr.sample.model.AverageSalaryByPosition;
import com.cubixedu.hr.sample.model.Company;
import com.cubixedu.hr.sample.model.Position;

public interface PositionRepository extends JpaRepository<Position, Integer> {

	public List<Position> findByName(String name);
}
