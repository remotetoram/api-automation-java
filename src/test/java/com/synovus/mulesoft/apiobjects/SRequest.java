package com.synovus.mulesoft.apiobjects;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.synovus.mulesoft.utils.HeaderHelper;
import com.synovus.mulesoft.utils.PayloadHelper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
public class SRequest {

	@Autowired
	private String sBaseUrl;
	@Autowired
	PayloadHelper payloadHelper;
	@Autowired
	HeaderHelper headerHelper;
	public String requestPayload;
    @Autowired
    private String testEnv;
	
	public Response sPostRequest(String sPostUri, String payLoad) {
		log.info("TestEnv >>" +testEnv);
		RestAssured.useRelaxedHTTPSValidation();
		RequestSpecification rs = RestAssured.given().baseUri(sBaseUrl).basePath(sPostUri);
		headerHelper.getSHeaders(rs);
		rs.body(payLoad);
		return rs.post();
	}
	 // Method to send SOAP request
    public Response sendSoapRequest(String soapEndpoint, String soapXMLPath) throws IOException {
    	RestAssured.useRelaxedHTTPSValidation();
    	String soapEnvelope = payloadHelper.convertXmlToString(soapXMLPath);
    	log.info("soapEnvelope="+soapEnvelope);
    	log.info("sBaseUrl="+sBaseUrl);
    	log.info("Inside SRequest.java soapEndpoint="+soapEndpoint);
        RequestSpecification rs = RestAssured.given().baseUri(sBaseUrl).basePath(soapEndpoint);
        headerHelper.getSoapHeaders(rs);
        rs.body(soapEnvelope);
        return rs.post(); 
        
    }
    

	public Response sGetRequest(String endpoint) {
		RequestSpecification rs = RestAssured.given().baseUri(sBaseUrl).basePath(endpoint);
		headerHelper.getSHeaders(rs);
		return rs.get();
	}

}
