package com.cubixedu.hr.sample.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.cubixedu.hr.sample.dto.HolidayRequestDto;
import com.cubixedu.hr.sample.model.HolidayRequest;


@Mapper(componentModel = "spring")
public interface HolidayRequestMapper {

	
	List<HolidayRequestDto> holidayRequestsToDtos(List<HolidayRequest> holidayRequests);	
	
	@Mapping(source = "employee.employeeId", target = "employeeId")
	@Mapping(source = "approver.employeeId", target = "approverId")	
	HolidayRequestDto holidayRequestToDto(HolidayRequest holidayRequest);

	@Mapping(target = "employee", ignore = true)
	@Mapping(target = "approver", ignore = true)
	HolidayRequest dtoToHolidayRequest(HolidayRequestDto holidayRequestDto);

	List<HolidayRequest> dtosToHolidayRequests(List<HolidayRequestDto> holidayRequestDtos);
	

}
