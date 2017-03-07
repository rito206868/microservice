package com.poc.springboot.employeedetails;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeDetailsController {
	
	@RequestMapping(value = "/employees/{empId}", method = RequestMethod.GET)
    public Employee getEmployeeNames(@PathVariable ("empId") String id) {
        return  EmployeeSource.getEmployee(id);
    }
}