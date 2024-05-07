package com.cubixedu.hr.sample.web;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.cubixedu.hr.sample.dto.HolidayRequestDto;
import com.cubixedu.hr.sample.dto.HolidayRequestFilterDto;
import com.cubixedu.hr.sample.mapper.EmployeeMapper;
import com.cubixedu.hr.sample.mapper.HolidayRequestMapper;
import com.cubixedu.hr.sample.model.HolidayRequest;
import com.cubixedu.hr.sample.service.HolidayRequestService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/holidayrequests")
public class HolidayRequestController {

	@Autowired
	HolidayRequestService holidayRequestService;

	@Autowired
	EmployeeMapper employeeMapper;

	@Autowired
	HolidayRequestMapper holidayRequestMapper;

	@GetMapping
	public List<HolidayRequestDto> getAll() {
		return holidayRequestMapper.holidayRequestsToDtos(holidayRequestService.findAll());
	}

	@GetMapping("/{id}")
	public HolidayRequestDto getById(@PathVariable long id) {
		HolidayRequest holidayRequest = holidayRequestService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		return holidayRequestMapper.holidayRequestToDto(holidayRequest);
	}

	@PostMapping(value = "/search")
	public List<HolidayRequestDto> findByExample(@RequestBody HolidayRequestFilterDto example, 
			Pageable pageable) {
		Page<HolidayRequest> page = holidayRequestService.findHolidayRequestsByExample(example, pageable);
		return holidayRequestMapper.holidayRequestsToDtos(page.getContent());
	}

	@PostMapping
	@PreAuthorize("#newHolidayRequest.employeeId == authentication.principal.employee.employeeId")
	public HolidayRequestDto addHolidayRequest(@RequestBody @Valid HolidayRequestDto newHolidayRequest) {
		HolidayRequest holidayRequest;
		try {
			holidayRequest = holidayRequestService.addHolidayRequest(holidayRequestMapper.dtoToHolidayRequest(newHolidayRequest), newHolidayRequest.getEmployeeId());
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		return holidayRequestMapper.holidayRequestToDto(holidayRequest);
	}

	@PutMapping("/{id}")
	@PreAuthorize("#newHolidayRequest.employeeId == authentication.principal.employee.employeeId")
	public HolidayRequestDto modifyHolidayRequest(@PathVariable long id, @RequestBody @Valid HolidayRequestDto modifiedHolidayRequest) {
		modifiedHolidayRequest.setEmployeeId(id);
		HolidayRequest holidayRequest;
		try {
			holidayRequest = holidayRequestService.modifyHolidayRequest(id, holidayRequestMapper.dtoToHolidayRequest(modifiedHolidayRequest));
		} catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED);
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

		return holidayRequestMapper.holidayRequestToDto(holidayRequest);
	}

	@DeleteMapping("/{id}")
	public void deleteHolidayRequest(@PathVariable long id) {
		try {
			holidayRequestService.deleteHolidayRequest(id);
		} catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED);
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping(value = "/{id}/approval", params = { "status"})
	public HolidayRequestDto approveHolidayRequest(@PathVariable long id, @RequestParam boolean status) {
		HolidayRequest holidayRequest;
		try {
			holidayRequest = holidayRequestService.approveHolidayRequest(id, status);
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		return holidayRequestMapper.holidayRequestToDto(holidayRequest);
	}

}
