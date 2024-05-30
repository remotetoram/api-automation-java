package com.synovus.mulesoft.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiPropertiesContext {
	@Value("${testEnv}")
	private String testEnv;

	@Bean("testEnv")
	public String getTestEnv() {
		return testEnv;
	}

	@Value("${api.synovus.base.url}")
	private String sBaseUrl;

	@Bean("sBaseUrl")
	public String getSBaseUrl() {
		return sBaseUrl;
	}

	@Value("${ocp.apim.subscription.key}")
	private String sApimSubscription;

	@Bean("sApimSubscription")
	public String getSApimSubscription() {
		return sApimSubscription;
	}
	@Value("${api.synovus.token.base.url}")
	private String sTokenBaseUrl;

	@Bean("sTokenBaseUrl")
	public String getSTokenBaseUrl() {
		return sTokenBaseUrl;
	}

	@Value("${api.synovus.token.uri}")
	private String sTokenUri;

	@Bean("sTokenUri")
	public String getSTokenUri() {
		return sTokenUri;
	}

}
