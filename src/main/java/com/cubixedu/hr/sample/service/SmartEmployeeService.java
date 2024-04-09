package com.cubixedu.hr.sample.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cubixedu.hr.sample.config.HrConfigProperties;
import com.cubixedu.hr.sample.config.HrConfigProperties.Smart;
import com.cubixedu.hr.sample.model.Employee;

@Service
public class SmartEmployeeService extends AbstractEmployeeService {

	@Autowired
	HrConfigProperties config;

	@Override
	public int getPayRaisePercent(Employee employee) {
		
		double yearsWorked = ChronoUnit.DAYS.between(employee.getDateOfStartWork(), LocalDateTime.now()) / 365.0;
		
		Smart smartConfig = config.getSalary().getSmart();
		
		//1. megoldás: fix limitek és percentek
//		if(yearsWorked > smartConfig.getLimit3())
//			return smartConfig.getPercent3();
//		
//		if(yearsWorked > smartConfig.getLimit2())
//			return smartConfig.getPercent2();
//		
//		if(yearsWorked > smartConfig.getLimit1())
//			return smartConfig.getPercent1();
		
		TreeMap<Double, Integer> raiseIntervals = smartConfig.getLimits();

		//2. megoldás: dinamikus limit/percent párosok
//		Integer maxLimit = null;
//		
//		for(var entry: raiseIntervals.entrySet()) {
//			if(yearsWorked > entry.getKey())
//				maxLimit = entry.getValue();
//			else
//				break;
//		}
//		
//		return maxLimit == null ? 0 : maxLimit;
		
		//3. megoldás: streammel
//		Optional<Double> optionalMax = raiseIntervals.keySet().stream()
//		.filter(k -> yearsWorked >= k)
//		.max(Double::compare);
//		
//		return optionalMax.isEmpty() ? 0 : raiseIntervals.get(optionalMax.get());
		
		
		//4. megoldás: TreeMap.floorEntry-vel
		Entry<Double, Integer> floorEntry = raiseIntervals.floorEntry(yearsWorked);
		return floorEntry == null ? 0 : floorEntry.getValue();
	}

}
