package com.esb.opensource.accenture.microservice;

import java.net.URI;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.http.common.HttpOperationFailedException;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ServiceCamelRoute extends RouteBuilder {

	@Value("${rest.host}")
	String host;
	@Value("${rest.port}")
	String port;
	@Value("${rest.target.url}")
	String EMPLYEE_SERVICE;
	@Value("${rest.post.url}")
	String EMPLYEE_TIME;

	@Override
	public void configure() throws Exception {

		onException(HttpOperationFailedException.class).handled(false).end();

		restConfiguration().component("jetty").host(host).port(port)
				.bindingMode(RestBindingMode.json);

		rest("/employee").post("/{id}")//.outType(EmployeeDetails.class)
				.to("direct:getDetail");

		from("direct:getDetail").process(new Processor() {

			@Override
			public void process(Exchange exchange) throws Exception {
				String empId = (String) exchange.getIn().getHeader("id");
				StringBuilder sb = new StringBuilder(EMPLYEE_SERVICE);
				sb.append(empId);
				sb.append("?bridgeEndpoint=true");
				URI uri = new URI(sb.toString());
				exchange.setProperty("employeeURI", uri);
			}
		}).removeHeaders("*").process(exchange -> {
			exchange.getIn().setBody(null);
		}).setHeader(Exchange.HTTP_METHOD, constant("GET"))
				.toD("${property.employeeURI}").to("direct:result");

		from("direct:result")
				.streamCaching()
				.log(LoggingLevel.INFO, "${body}")
				.process(
						exchange -> {
							String jsonResponse = exchange.getIn().getBody(
									String.class);
							//ObjectMapper objectMapper = new ObjectMapper();
							/*
							 * objectMapper.configure(SerializationFeature.
							 * FAIL_ON_EMPTY_BEANS, false);
							 * objectMapper.configure(Feature.AUTO_CLOSE_SOURCE,
							 * true);
							 */
							/*EmployeeDetails emp = objectMapper.readValue(
									jsonResponse, EmployeeDetails.class);*/
							exchange.getOut().setHeader(Exchange.CONTENT_TYPE, "application/json");
							exchange.getOut().setBody(jsonResponse);
						})
				.setHeader(Exchange.HTTP_METHOD, constant("POST"))
				.to(EMPLYEE_TIME+"?bridgeEndpoint=true").transform().constant("Status:Success");			
	}
}
