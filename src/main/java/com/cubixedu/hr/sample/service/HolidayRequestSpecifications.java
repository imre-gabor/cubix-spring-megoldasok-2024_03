package com.cubixedu.hr.sample.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.Specification;

import com.cubixedu.hr.sample.model.Employee_;
import com.cubixedu.hr.sample.model.HolidayRequest;
import com.cubixedu.hr.sample.model.HolidayRequest_;


public class HolidayRequestSpecifications {

	public static Specification<HolidayRequest> hasApproved(Boolean approved) {
		return (root, cq, cb) -> cb.equal(root.get(HolidayRequest_.approved), approved);
	}

	public static Specification<HolidayRequest> createDateIsBetween(LocalDateTime createDateTimeStart,
			LocalDateTime createDateTimeEnd) {
		return (root, cq, cb) -> cb.between(root.get(HolidayRequest_.createdAt), createDateTimeStart, createDateTimeEnd);
	}

	public static Specification<HolidayRequest> hasEmployeeName(String employeeName) {
		return (root, cq, cb) -> cb.like(cb.lower(root.get(HolidayRequest_.employee).get(Employee_.name)),
				(employeeName + "%").toLowerCase());
	}

	public static Specification<HolidayRequest> hasApprovalName(String approvalName) {
		return (root, cq, cb) -> cb.like(cb.lower(root.get(HolidayRequest_.approver).get(Employee_.name)),
				(approvalName + "%").toLowerCase());
	}

	public static Specification<HolidayRequest> isStartDateLessThan(LocalDate date) {
		return (root, cq, cb) -> cb.lessThan(root.get(HolidayRequest_.startDate), date);
	}
	
	public static Specification<HolidayRequest> isEndDateGreaterThan(LocalDate date) {
		return (root, cq, cb) -> cb.greaterThan(root.get(HolidayRequest_.endDate), date);
	}

}
