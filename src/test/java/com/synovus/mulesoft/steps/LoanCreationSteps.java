package com.synovus.mulesoft.steps;

import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.synovus.mulesoft.apiobjects.ResponseHolder;
import com.synovus.mulesoft.apiobjects.SRequest;
import com.synovus.mulesoft.utils.TestDataConfig;
import com.synovus.mulesoft.utils.Validations;
import io.cucumber.java.en.And;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoanCreationSteps {
	@Autowired
	private SRequest sRequest;
	@Autowired
	private ResponseHolder responseHolder;
	@Autowired
	private Validations validations;
	@Autowired
	private TestDataConfig testDataConfig;
	String resourceEndpoint;
	String soapEndpoint;
	String[] detail;
	List<String> successMsg = new LinkedList<>();
	List<Integer> returnCode = new LinkedList<>();


	@And("Validate generateLNAccntNum API response")
	public void response_contain_correct_values_for_generateLNAccntNum_API() throws Exception {
		validations.validateFieldValue("accountNumber.branch",validations.getFieldValueFromPropertiesFIle("accountNumber_branch","loancreation"), responseHolder.getResponse());
		validations.validateFieldValue("accountNumber.acctType", validations.getFieldValueFromPropertiesFIle("accountNumber_acctType","loancreation"), responseHolder.getResponse());
		validations.validateLongFieldValue("accountNumber.acctNbr", responseHolder.getResponse());
		long generatedLoanAcct= responseHolder.getResponse().jsonPath().getInt("accountNumber.acctNbr");
	    log.info("generatedLoanAcct:>> " + generatedLoanAcct);
	    testDataConfig.addValueInAdditionalTestData("lnAcctNbr", String.valueOf(generatedLoanAcct)); //As of not loan system generated number is not working so adding 1

		
	}
	
	@And("Validate openLNAccnt API response")
	public void response_contain_correct_values_for_openLNAccnt_API() throws Exception {
		validations.validateFieldValue("account.lnBrnchNbr",validations.getFieldValueFromPropertiesFIle("lnBrnchNbr","loancreation"), responseHolder.getResponse());
		validations.validateFieldValue("account.lnAcctTyp", validations.getFieldValueFromPropertiesFIle("lnAcctTyp","loancreation"), responseHolder.getResponse());
		validations.validateLongFieldValue("account.lnAcctNbr", responseHolder.getResponse());
		
	}

	}