package com.synovus.mulesoft.steps;

import org.springframework.beans.factory.annotation.Autowired;
import com.synovus.mulesoft.utils.*;
import com.synovus.mulesoft.apiobjects.ResponseHolder;
import io.cucumber.java.en.And;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StopPaymentsSteps {
	@Autowired
	private ResponseHolder responseHolder;
	@Autowired
	private Validations validations;
	@Autowired
	private TestDataConfig testDataConfig;
	//private String newlyCreatedStopPaymentID;

	@And("Response contains correct values for addStop API")
	public void response_contain_correct_values_for_addStop_API() throws Exception {
		validations.validateFieldValue("stopPayments.dpAcctNbr", validations.getFieldValueFromPropertiesFIle("dpAcctNbr","stoppayments"), responseHolder.getResponse());
		validations.validateFieldValue("stopPayments.dpStpPmtResn",  validations.getFieldValueFromPropertiesFIle("dpStpPmtResn","stoppayments"), responseHolder.getResponse());
		validations.validateFieldValue("stopPayments.dpStpPmtPye",  validations.getFieldValueFromPropertiesFIle("dpStpPmtPye","stoppayments"), responseHolder.getResponse());
		validations.validateDateFieldValue("stopPayments.dpStpPmtExpireDte",responseHolder.getResponse());
	    testDataConfig.addValueInAdditionalTestData("dpStpPmtId", String.valueOf(responseHolder.getResponse().jsonPath().getInt("stopPayments.dpStpPmtId")));
		log.info("newlyCreatedStopPaymentID:>> " + testDataConfig.getValueFromAdditionalTestData("dpStpPmtId"));
	}
	@And("Response contains newly added stop Payment")
	public void response_contain_newly_added_stop_paymentsI() throws Exception {
		log.info("newlyCreatedStopPaymentID:in test Method>> " + testDataConfig.getValueFromAdditionalTestData("dpStpPmtId"));
		validations.fieldValueValidatorinResponse("dpStpPmtId", testDataConfig.getValueFromAdditionalTestData("dpStpPmtId"), responseHolder.getResponse());
	}
	@And("Response contains correct values for getStop API")
	public void response_contain_correct_values_for_getStop_API() throws Exception {
		validations.validateFieldValue("stopPayments.dpAcctNbr", validations.getFieldValueFromPropertiesFIle("dpAcctNbr","stoppayments"), responseHolder.getResponse());
		validations.validateFieldValue("stopPayments.dpStpPmtResn",  validations.getFieldValueFromPropertiesFIle("dpStpPmtResn","stoppayments"), responseHolder.getResponse());
		validations.validateFieldValue("stopPayments.dpStpPmtPye",  validations.getFieldValueFromPropertiesFIle("dpStpPmtPye","stoppayments"), responseHolder.getResponse());
		validations.validateDateFieldValue("stopPayments.dpStpPmtExpireDte",responseHolder.getResponse());
	}
	
}