package com.poc.springboot.employeedetails;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class EmployeeSource {
    private static final Map<String, Employee> EMPLOYEES = new HashMap<>();
    static {
        Employee emp1 = new Employee();
        emp1.setId("1");
        emp1.setFirstName("Joe");
        emp1.setLastName("McCartney");
        emp1.setEmail("iamjoe@aop.com");
        emp1.setAddress("Austin");
        emp1.setAge(32);
        
        Employee emp2 = new Employee();
        emp2.setId("2");
        emp2.setFirstName("Pablo");
        emp2.setLastName("Rodrigez");
        emp2.setEmail("hastalavista@aop.com");
        emp2.setAddress("Texas");
        emp2.setAge(41);
        
        Employee emp3 = new Employee();
        emp3.setId("3");
        emp3.setFirstName("Shawn");
        emp3.setLastName("Bach");
        emp3.setEmail("bach@aop.com");
        emp3.setAddress("Ohio");
        emp3.setAge(22);
        
        EMPLOYEES.put(emp1.getId(), emp1);
        EMPLOYEES.put(emp2.getId(), emp2);
        EMPLOYEES.put(emp3.getId(), emp3);
    }
    
/*    public static Collection<Employee> getEmployees() {
        return EMPLOYEES.values();
    }*/
    
    public static Employee getEmployee(String empId) {
        return EMPLOYEES.get(empId);
    }
}
