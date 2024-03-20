package com.cubixedu.hr.sample.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cubixedu.hr.sample.dto.EmployeeDto;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

	private Map<Long, EmployeeDto> employees = new HashMap<>();	
	
	{
		employees.put(1L, new EmployeeDto(1, "abc", "manager", 1000000, null));
		employees.put(2L, new EmployeeDto(1, "def", "developer", 5000000, null));
	}
	
	
	//1. megoldás
//	@GetMapping
//	public List<EmployeeDto> findAll(){
//		return new ArrayList<>(employees.values());
//	}
//	
//	
//	@GetMapping(params = "minSalary")
//	public List<EmployeeDto> findBySalary(@RequestParam int minSalary){
//		return employees.values().stream().filter(e -> e.getSalary() > minSalary).toList();
//	}
//	
	//2. megoldás	
	@GetMapping
	public List<EmployeeDto> findAll(@RequestParam Optional<Integer> minSalary){
		if(minSalary.isPresent()) {
			return employees.values().stream().filter(e -> e.getSalary() > minSalary.get()).toList();
		} else {		
			return new ArrayList<>(employees.values());
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<EmployeeDto> findById(@PathVariable long id) {
		EmployeeDto employeeDto = employees.get(id);
		if(employeeDto == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(employeeDto);
	}
	
	@PostMapping
	public ResponseEntity<EmployeeDto> create(@RequestBody EmployeeDto employee) {
		if(employees.containsKey(employee.getId()))
			return ResponseEntity.badRequest().build();
			
		employees.put(employee.getId(), employee);
		return ResponseEntity.ok(employee);
	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<EmployeeDto> update(@PathVariable long id, @RequestBody EmployeeDto employee) {
		employee.setId(id);
		if(!employees.containsKey(id))
			return ResponseEntity.notFound().build();
		
		employees.put(id, employee);
		return ResponseEntity.ok(employee);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable long id) {
		employees.remove(id);
	}
	
}
