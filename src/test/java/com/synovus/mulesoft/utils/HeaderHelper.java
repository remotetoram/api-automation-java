package com.synovus.mulesoft.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.synovus.mulesoft.apiobjects.SRequest;

import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
public class HeaderHelper {
	   @Autowired
	    private Token token;
	    @Autowired
	    private String testEnv;
	    @Autowired
	    private String sApimSubscription;


	public RequestSpecification getSHeaders(RequestSpecification rs) {
		rs.header("Content-Type", "application/json");
		rs.header("transactionID", "62B7FC46-84EE-4C46-BD81-2E31F665BBA3");
		 if (testEnv.equalsIgnoreCase("QAAPIM")) {
			 log.info("sBaseUrl in HeaderHelper: " + sApimSubscription);
			 rs.header("Authorization", "Bearer " +token.getAuthToken());
			 rs.header("Ocp-Apim-Subscription-Key", sApimSubscription);
		 }
		
		return rs;
	}
	
	public RequestSpecification getSoapHeaders(RequestSpecification rs) {
		rs.header("Content-Type", "text/xml; charset=utf-8");
		return rs;
	}

}