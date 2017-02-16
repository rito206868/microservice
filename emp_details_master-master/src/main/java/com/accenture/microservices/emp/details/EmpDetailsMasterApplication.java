package com.accenture.microservices.emp.details;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.accenture.microservices.emp.details.data.EmployeeDetails;
import com.accenture.microservices.emp.details.data.repository.EmployeeRepository;
import com.accenture.microservices.emp.details.service.CorrelationHeaderFilter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication(scanBasePackages = {"com.accenture.microservices"})
@EnableCircuitBreaker
@EnableDiscoveryClient
public class EmpDetailsMasterApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmpDetailsMasterApplication.class, args);
	}
	@Bean
	public Docket newAPIChargeCode() {
	        return new Docket(DocumentationType.SWAGGER_2)
	                     
	                     .groupName("employee")
	                     .apiInfo(apiInfo())
	                .select()
	                .paths(regex("/employees.*"))
	                .build();
	                 
	   }
	@Component
	@Primary
	public class CustomObjectMapper extends ObjectMapper {
	    public CustomObjectMapper() {
	        setSerializationInclusion(JsonInclude.Include.NON_NULL);
	        configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	        configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
	        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	        enable(SerializationFeature.INDENT_OUTPUT);
	    }
	}


	private ApiInfo apiInfo() {
	        return new ApiInfoBuilder()
	            .title("Employee Details Micorservice Code API")
	            .description("This API is for validating and fetching employee details data for authorized employee with in organization.")
	            .version("1.0")
	            .contact(new Contact("ATA Lean Architecture Team", "", "ATA.Lean.Arch.Group@accenture.com"))
	                   //.contact("ATA Lean Architecture Team")
	            .license("Accenture License Version")
	            //.licenseUrl("https://github.com/springfox/springfox/blob/master/LICENSE")
	             .build();
	    }

	private static final Logger log = LoggerFactory.getLogger(EmpDetailsMasterApplication.class);

		  @Bean
		public CommandLineRunner demo(EmployeeRepository repository){
	    	return (args) ->{
	    		repository.save(new EmployeeDetails("Jakie", "Ezu", 21, "bangalore", "jakie.ezu@gmail.com", 1233));
	    		repository.save(new EmployeeDetails("Ann", "Jee", 34, "Chennai", "ann.jeeu@gmail.com", 1234));
	    		repository.save(new EmployeeDetails("Bann", "Hee", 36, "Hydrabad", "bann.hee@gmail.com", 1235));
	    		repository.save(new EmployeeDetails("Cann", "lee", 25, "Mumbai", "cann.lee@gmail.com", 1245));
	    		repository.save(new EmployeeDetails("Dann", "lee", 28, "Delhi", "dann.lee@gmail.com", 1246));
	    		repository.save(new EmployeeDetails("Kann", "Mde", 45, "Kolkata", "kann.mde@gmail.com", 1247));
	    		repository.save(new EmployeeDetails("Zann", "Nke", 53, "Cochin", "zann.nke@gmail.com", 1248));
	    		
	    		log.info("Employees");
	       		for (EmployeeDetails Employee : repository.findAll()) {
	    			log.info(Employee.toString());
	    	}
	     };   	
	    }
		  @Bean
		    public FilterRegistrationBean correlationHeaderFilter() {
		        FilterRegistrationBean filterRegBean = new FilterRegistrationBean();
		        filterRegBean.setFilter(new CorrelationHeaderFilter());
		        filterRegBean.setUrlPatterns(Arrays.asList("/*"));

		        return filterRegBean;
		    }
}
