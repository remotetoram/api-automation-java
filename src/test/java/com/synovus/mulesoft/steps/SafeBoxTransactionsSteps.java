package com.synovus.mulesoft.steps;

import org.springframework.beans.factory.annotation.Autowired;
import com.synovus.mulesoft.utils.*;
import com.synovus.mulesoft.velocity.JsonPayloadGenerator;
import com.synovus.mulesoft.apiobjects.ResponseHolder;
import com.synovus.mulesoft.apiobjects.SRequest;

import io.cucumber.java.en.And;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SafeBoxTransactionsSteps {
	@Autowired
	private SRequest sRequest;
//	@Autowired
//	private SafeBoxTxn safeBoxTxn;
	@Autowired
	private ResponseHolder responseHolder;
	@Autowired
	private Validations validations;
	String resourceEndpoint;
	String soapEndpoint;
	@Autowired
	private TestDataConfig testDataConfig;
	@Autowired
	private JsonPayloadGenerator jsonPayloadGenerator;
//
//	@Given("User has created payload with {string} template with data from {string} file using model")
//	public void user_has_payload_pay_for_endpoint_using_model(String templateFileName, String testDataFileName)
//			throws IOException {
//		Map<String, String> basetestdata = testDataConfig.getLoadedProperties().get("src\\test\\resources\\testdata\\basedata.properties");
//		Map<String, String> properties = testDataConfig.getLoadedProperties()
//				.get("src\\test\\resources\\testdata\\" + testDataFileName + ".properties");
//		
//		if (properties == null) {
//			System.err.println("Properties file " + testDataFileName + " not loaded.");
//			return;
//		}
//		safeBoxTxn.setBnkNumber(basetestdata.get("bnkNumber"));
//		safeBoxTxn.setApplKy(basetestdata.get("applKy"));
//		safeBoxTxn.setOpID(basetestdata.get("opID"));
//		safeBoxTxn.setDesc1(basetestdata.get("desc1"));
//		safeBoxTxn.setDesc2(basetestdata.get("desc2"));
//		safeBoxTxn.setChannel(basetestdata.get("channel"));
//		safeBoxTxn.setBranch(properties.get("branch"));
//		safeBoxTxn.setBoxPrefix(properties.get("boxPrefix"));
//		safeBoxTxn.setBoxNumber(properties.get("boxNumber"));
//		sRequest.requestPayload = jsonPayloadGenerator.generateJsonPayloadUsingModel(templateFileName, safeBoxTxn);
//	}

	@And("Response contains correct values for getSafeBoxOwner API")
	public void response_contain_correct_values_for_getSafeBoxTxn_API() throws Exception {
		
		validations.validateFieldValue("boxOwnerData.brnch", validations.getFieldValueFromPropertiesFIle("branch","safebox"), responseHolder.getResponse());
		validations.validateFieldValue("boxOwnerData.boxPrfx", validations.getFieldValueFromPropertiesFIle("boxPrefix","safebox"), responseHolder.getResponse());
		validations.validateFieldValue("boxOwnerData.boxNbr", validations.getFieldValueFromPropertiesFIle("boxNumber","safebox"), responseHolder.getResponse());

	}
	@And("Response contains correct values for getSafeBoxTxn API")
	public void response_contain_correct_values_for_getSafeBoxOwner_API() throws Exception {
		validations.validateFieldValue("boxTransactionsData.brnch", validations.getFieldValueFromPropertiesFIle("branch","safebox"), responseHolder.getResponse());
		validations.validateFieldValue("boxTransactionsData.boxPrfx", validations.getFieldValueFromPropertiesFIle("boxPrefix","safebox"), responseHolder.getResponse());
		validations.validateFieldValue("boxTransactionsData.boxNbr", validations.getFieldValueFromPropertiesFIle("boxNumber","safebox"), responseHolder.getResponse());

	}
	@And("Response contains correct values for getSafeBoxDeputy API")
	public void response_contain_correct_values_for_getSafeBoxDeputy_API() throws Exception {
		validations.validateFieldValue("boxDeputies.brnch", validations.getFieldValueFromPropertiesFIle("branch","safebox"), responseHolder.getResponse());
		validations.validateFieldValue("boxDeputies.boxPrfx", validations.getFieldValueFromPropertiesFIle("boxPrefix","safebox"), responseHolder.getResponse());
		validations.validateFieldValue("boxDeputies.boxNbr", validations.getFieldValueFromPropertiesFIle("boxNumber_2","safebox"), responseHolder.getResponse());

	}

}