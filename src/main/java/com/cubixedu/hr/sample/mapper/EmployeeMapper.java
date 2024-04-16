package com.cubixedu.hr.sample.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.cubixedu.hr.sample.dto.EmployeeDto;
import com.cubixedu.hr.sample.model.Employee;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
		
	List<EmployeeDto> employeesToDtos(List<Employee> employees);

	@Mapping(target = "id", source = "employeeId")
	@Mapping(target = "title", source = "position.name")
	@Mapping(target = "entryDate", source = "dateOfStartWork")
	@Mapping(target = "company.employees", ignore = true)
	EmployeeDto employeeToDto(Employee employee);

	@InheritInverseConfiguration
	Employee dtoToEmployee(EmployeeDto employeeDto);

	List<Employee> dtosToEmployees(List<EmployeeDto> employees);

}
