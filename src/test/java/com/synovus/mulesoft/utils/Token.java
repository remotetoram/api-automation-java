package com.synovus.mulesoft.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;

@Slf4j
@Component
@ContextConfiguration(classes = { ApiPropertiesContext.class })
public class Token {
	@Autowired
	private String sTokenBaseUrl;
	@Value("${OAUTH_CLIENT_ID}")
	private String clientId;
	@Value("${OAUTH_CLIENT_SECRET}")
	private String secret;
	@Autowired
	private String sTokenUri;

	public String getAuthToken() {
		RequestSpecification forToken = RestAssured.given().baseUri(sTokenBaseUrl);
		forToken.header("Content-Type", "application/x-www-form-urlencoded");
		forToken.auth().preemptive().basic(clientId, secret);
		forToken.formParams("grant_type", "client_credentials");
		Response response = forToken.post(sTokenBaseUrl + sTokenUri);
		log.info("API Response code>> " + response.getStatusCode());
		ResponseBody tokenBody = response.getBody();
		log.info("TokenBody: " + tokenBody);
		JsonPath tokenResp = new JsonPath(tokenBody.asString());
		log.info("Token generated: " + tokenResp);
		return tokenResp.get("access_token");
	}
}
