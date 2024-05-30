package com.synovus.mulesoft.ado;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@ComponentScan("com.synovus.ado")
@Configuration
@PropertySource("classpath:adoconfiguration.properties")
public class Config {

	@Value("${baseApiUrl}")
	private String baseApiUrl;

	@Bean("baseApiUrl")
	public String getBaseURL() {
		return baseApiUrl;
	}

	@Value("${testPlansUrlTemplate}")
	private String testPlansUrlTemplate;

	@Bean("testPlansUrlTemplate")
	public String getTestPlansUrl() {
		return testPlansUrlTemplate;
	}

	@Value("${testPointsUrlTemplate}")
	private String testPointsUrlTemplate;

	@Bean("testPointsUrlTemplate")
	public String getTestPointsUrl() {
		return testPointsUrlTemplate;
	}

	@Value("${testRunUrlTemplate}")
	private String testRunUrlTemplate;

	@Bean("testRunUrlTemplate")
	public String getTestRunUrl() {
		return testRunUrlTemplate;
	}

	@Value("${testResultIdUrlTemplate}")
	private String testResultIdUrlTemplate;

	@Bean("testResultIdUrlTemplate")
	public String getTestResultId() {
		return testResultIdUrlTemplate;
	}

	@Value("${testRunResultsUrlTemplate}")
	private String testRunResultsUrlTemplate;

	@Bean("testRunResultsUrlTemplate")
	public String getTestRunResultsUrl() {
		return testRunResultsUrlTemplate;
	}

	@Value("${runPayloadTemplate}")
	private String runPayloadTemplate;

	@Bean("runPayloadTemplate")
	public String getRunPayload() {
		return runPayloadTemplate;
	}

	@Value("${resultPayloadTemplate}")
	private String resultPayloadTemplate;

	@Bean("resultPayloadTemplate")
	public String getResultRunPayload() {
		return resultPayloadTemplate;
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}
