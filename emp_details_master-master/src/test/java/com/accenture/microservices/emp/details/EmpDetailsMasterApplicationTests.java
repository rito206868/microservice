package com.accenture.microservices.emp.details;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@SpringBootTest (classes = EmpDetailsMasterApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class EmpDetailsMasterApplicationTests {

public static final Logger log = LoggerFactory.getLogger(EmpDetailsMasterApplicationTests.class);
	
	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));
	

	private MockMvc mockMvc;
    
//	private HttpMessageConverter mappingJackson2HttpMessageConverter;
    
	@Autowired
    private WebApplicationContext webApplicationContext;
    
    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }
	
    /**
     * 
     * @throws Exception
     * @Description: positive test case- Unit test with parameter employeeId
     * @param: employeeId
     * @return: EmployeeDetails
     * @expected result: EmployeeDetails document
     * 
     */
	@Test
	public void getEmployeeDetails() throws Exception {
		log.info("Result::: "+mockMvc.perform(get("/employees/1")));
		MvcResult result = mockMvc.perform(get("/employees/1")).andDo(print()).andReturn();
		log.info(result.getResponse().getContentAsString());
	}

		
	/**
	 * 
	 * @throws Exception
	 * @Description: positive test case- Unit Test with out employeeId
	 * @param: null
     * @return: EmployeeDetails arraylist
     * @expected result: arraylist of EmployeeDetails  document 
	 */
	@Test
	public void getAllEmployees() throws Exception {
		log.info(" Inside getAllEmployees");
//		List<EmployeeDetails> emplList = new ArrayList<EmployeeDetails>();
		mockMvc.perform(get("/employees")).andExpect(status().isOk()).andDo(print()).andExpect(content().contentType(contentType));
	}
	
	  /**
     * 
     * @throws Exception
     * @Description: negative test case- Unit test with parameter employeeId
     * @param: employeeId
     * @return: null
     * @expected result: 404 
     * 
     */	
	@Test
	public void getEmployeeDetailsURLCheck() throws Exception {
		log.info(" Inside getEmployeeDetailsURLCheck");
		int employeeId = 1233;
		mockMvc.perform(get("/employees/Details/"+employeeId)).andExpect(status().isNotFound()).andDo(print());
		
	}
	
	 /**
     * 
     * @throws Exception
     * @Description: positive test case- Unit test with parameter employeeId
     * @param: employeeId
     * @return: true
     * @expected result: Employee Does not Exist. Please provide an existing employee
     * 
     */
	@Test
	public void deleteEmployeeDetails() throws Exception {
		MvcResult result = mockMvc.perform(delete("/employees/1")).andDo(print()).andReturn();
		log.info(result.getResponse().getContentAsString());
	}
	
	 /**
     * 
     * @throws Exception
     * @Description: negative test case- Unit test with parameter employeeId
     * @param: employeeId
     * @return: false
     * @expected result: false
     * 
     */
	@Test
	public void deleteEmployee() throws Exception {
		MvcResult result = mockMvc.perform(delete("/employees/1233")).andDo(print()).andReturn();
		log.info(result.getResponse().getContentAsString());
	}

}