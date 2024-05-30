package com.synovus.mulesoft.steps;

import static org.assertj.core.api.Assertions.assertThat;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.synovus.mulesoft.utils.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.synovus.mulesoft.apiobjects.ResponseHolder;
import com.synovus.mulesoft.apiobjects.SRequest;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.extern.slf4j.Slf4j;
import com.synovus.mulesoft.dbconnection.models.*;

@Slf4j
public class CommonSteps {
	@Autowired
	private SRequest sRequest;
	@Autowired
	private PayloadHelper payloadHelper;
	@Autowired
	private ResponseHolder responseHolder;
	@Autowired
	private Validations validations;
	@Autowired
	private JsonHelper jsonHelper;
	@Autowired
	public EndpointReader endpointReader;
	private List<EmployeeTb> resultList;
	String resourceEndpoint;
	String soapEndpoint;
	String[] detail;
	List<String> successMsg = new LinkedList<>();
	List<Integer> returnCode = new LinkedList<>();
	@Given("User has created payload with {string} template with data from {string} file")
	public void user_has_payload_pay_for_endpoint(String templateFileName, String testDataFileName) throws IOException {
		sRequest.requestPayload = payloadHelper.createPayload(templateFileName, testDataFileName);
	}
	
	@Given("User has created payload with {string} template with data from {string} file with data generated earlier")
	public void user_has_payload_pay_for_endpoint_with_additional_TestData(String templateFileName, String testDataFileName) throws IOException {
		sRequest.requestPayload = payloadHelper.createPayloadWithAddtionalTestData(templateFileName, testDataFileName);
	}

	@Given("user sends REST request with {string} endpoint")
	public void user_sends_REST_request_with_endpoint(String endpoint) throws IOException {
		resourceEndpoint = endpointReader.getEndpoint(endpoint);
		log.info("resourceEndpoint:" + resourceEndpoint);
	    responseHolder.setResponse(sRequest.sPostRequest(resourceEndpoint, sRequest.requestPayload));
	}

	@Then("API response status code is {int}")
	public void api_response_status_code_is_200(int expectedcode) throws JsonProcessingException {
		log.info("resourceEndpoint:" + responseHolder.getResponse().getBody().asString());
	   validations.ResponseCodeValidator(expectedcode, responseHolder.getResponse());
	}

	@Then("Validate that API response has error message {string}")
	public void api_response_error_message(String expectedErrorMsg) throws JsonProcessingException {
		validations.ResponseErrorValidator(responseHolder.getResponse(), expectedErrorMsg);
	}

	@And("Rest contract is valid for {string} endpoint")
	public void rest_contract_is_valid(String jsonContractFile) throws Exception {
	   // log.info("Response Body>>" + responseHolder.getResponse().getBody().asString());
		validations.ResponseRestSchemaValidator(jsonContractFile, responseHolder.getResponse());
	}
	
	
	@Given("user sends SOAP request with {string} endpoint with XML {string}")
	public void user_sends_SOAP_request_with_endpoint(String endpoint, String xmlPath) throws IOException {
		soapEndpoint = endpointReader.getEndpoint(endpoint);
		responseHolder.setResponse(sRequest.sendSoapRequest(soapEndpoint, xmlPath));
	 }



	@And("SOAP contract is valid for endpoint {string}")
	public void soap_contract_is_valid(String endpoint) throws Exception {
		validations.ResponseSoapSchemaValidator(endpoint + ".xsd", responseHolder.getResponse());
	}

	@Given("I execute query to get employee with id as {string}")
	public void execute_query_to_get_employee_with_id(String id) throws JsonProcessingException {
		// resultList= HibernateUtil.RunDataBaseQuery("FROM Employee WHERE
		// Id="+id,Employee.class)
		resultList = testData();
	}

	@Then("DB query result is valid")
	public void api_db_query_gets_same_employee() throws JsonProcessingException {
		assertThat(jsonHelper.getNamesFromResultList(resultList)).contains("Alex Jhon");
	}

	public List<EmployeeTb> testData() {
		List<EmployeeTb> employeeList = new ArrayList<>();
		employeeList.add(new EmployeeTb(4, false, "Alex Jhon", "Sr Consultant"));
		return employeeList;
	}

}