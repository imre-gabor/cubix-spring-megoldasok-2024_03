package com.cubixedu.hr.sample.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.cubixedu.hr.sample.dto.HolidayRequestFilterDto;
import com.cubixedu.hr.sample.model.Employee;
import com.cubixedu.hr.sample.model.HolidayRequest;
import com.cubixedu.hr.sample.repository.HolidayRequestRepository;
import com.cubixedu.hr.sample.security.HrUser;


@Service
public class HolidayRequestService {

	@Autowired
	HolidayRequestRepository holidayRequestRepository;

	@Autowired
	EmployeeService employeeService;

	public List<HolidayRequest> findAll() {
		return holidayRequestRepository.findAll();
	}

	public Optional<HolidayRequest> findById(long id) {
		return holidayRequestRepository.findById(id);
	}

	public Page<HolidayRequest> findHolidayRequestsByExample(HolidayRequestFilterDto example, Pageable pageable) {
		LocalDateTime createDateTimeStart = example.getCreateDateTimeStart();
		LocalDateTime createDateTimeEnd = example.getCreateDateTimeEnd();
		String employeeName = example.getEmployeeName();
		String approvalName = example.getApproverName();
		Boolean approved = example.getApproved();
		LocalDate startOfHolidayRequest = example.getStartDate();
		LocalDate endOfHolidayRequest = example.getEndDate();

		Specification<HolidayRequest> spec = Specification.where(null);

		if (approved != null)
			spec = spec.and(HolidayRequestSpecifications.hasApproved(approved));
		if (createDateTimeStart != null && createDateTimeEnd != null)
			spec = spec.and(HolidayRequestSpecifications.createDateIsBetween(createDateTimeStart, createDateTimeEnd));
		if (StringUtils.hasText(employeeName))
			spec = spec.and(HolidayRequestSpecifications.hasEmployeeName(employeeName));
		if (StringUtils.hasText(approvalName))
			spec = spec.and(HolidayRequestSpecifications.hasApprovalName(approvalName));
		if (startOfHolidayRequest != null)
			spec = spec.and(HolidayRequestSpecifications.isEndDateGreaterThan(startOfHolidayRequest));
		if (endOfHolidayRequest != null)
			spec = spec.and(HolidayRequestSpecifications.isStartDateLessThan(endOfHolidayRequest));
		return holidayRequestRepository.findAll(spec, pageable);
	}

	@Transactional
	public HolidayRequest addHolidayRequest(HolidayRequest holidayRequest, long employeeId) {
		Employee employee = employeeService.findById(employeeId).get();
		employee.addHolidayRequest(holidayRequest);
		holidayRequest.setCreatedAt(LocalDateTime.now());
		return holidayRequestRepository.save(holidayRequest);
	}

	@Transactional
	public HolidayRequest approveHolidayRequest(long id, boolean status) {
		HolidayRequest holidayRequest = holidayRequestRepository.findById(id).get();
		Employee manager = holidayRequest.getEmployee().getManager();
		Long approverId = getCurrentHrUser().getEmployee().getEmployeeId();
		if(manager == null || !manager.getEmployeeId().equals(approverId))
			throw new AccessDeniedException("Only manager of employee can approve a holiday request");
		holidayRequest.setApprover(employeeService.findById(approverId).get());
		holidayRequest.setApproved(status);
		holidayRequest.setApprovedAt(LocalDateTime.now());
		return holidayRequest;
	}

	@Transactional
	public HolidayRequest modifyHolidayRequest(long id, HolidayRequest newHolidayRequest) {
		HolidayRequest holidayRequest = holidayRequestRepository.findById(id).get();
		if (holidayRequest.getApproved() != null)
			throw new IllegalStateException();
		holidayRequest.setEndDate(newHolidayRequest.getEndDate());
		holidayRequest.setStartDate(newHolidayRequest.getStartDate());
		holidayRequest.setCreatedAt(LocalDateTime.now());
		return holidayRequest;
	}

	@Transactional
	public void deleteHolidayRequest(long id) {
		HolidayRequest holidayRequest = holidayRequestRepository.findById(id).get();
		if(!holidayRequest.getEmployee().getEmployeeId().equals(getCurrentHrUser().getEmployee().getEmployeeId()))
			throw new AccessDeniedException("Only own holiday requests can be deleted");
		
		if (holidayRequest.getApproved() != null)
			throw new IllegalArgumentException();
		holidayRequest.getEmployee().getHolidayRequests().remove(holidayRequest);
		holidayRequestRepository.deleteById(id);
	}
	
	
	private HrUser getCurrentHrUser() {
		return (HrUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

}
