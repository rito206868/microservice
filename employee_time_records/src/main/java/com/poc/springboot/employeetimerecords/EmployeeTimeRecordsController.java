package com.poc.springboot.employeetimerecords;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class EmployeeTimeRecordsController {
	
	@RequestMapping(method = RequestMethod.POST)
    public void CreateEmployee(@RequestBody String employee) {
        //System.out.println(lstEmployee);
        try {
	        	/*JSONArray jsonarr = new JSONArray(lstEmployee);
	        	System.out.println("Length = " + jsonarr.length());*/
        	
        	ObjectMapper objectMapper = new ObjectMapper();
        	EmployeeDetails emp = objectMapper.readValue(employee, EmployeeDetails.class);
        	displayEmployee(emp);
	        	
	            /*for(int i = 0; i < jsonarr.length(); i++){
		            JSONObject jsonobj = jsonarr.getJSONObject(i);
		            ObjectMapper mapper = new ObjectMapper();
		            EmployeeDetails employee = mapper.readValue(jsonobj.toString(), EmployeeDetails.class);
		            displayEmployee(employee);
	            }*/
			} catch (Exception e) {
			e.printStackTrace();
		}
    }

	private void displayEmployee(EmployeeDetails employee){
		
		System.out.println("==========================================");
		System.out.println("Id : " + employee.getId());		
		System.out.println("First Name : " + employee.getFirstName());		
		System.out.println("Last Name : " + employee.getLastName());
		System.out.println("Address : " + employee.getAddress());	
		System.out.println("Age : " + employee.getAge());	
		System.out.println("Email : " + employee.getEmail());	
		System.out.println("==========================================");
	}
}
