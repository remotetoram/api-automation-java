package com.synovus.mulesoft.ado;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AzureDevOpsUrlBuilder {

	@Autowired
	private String baseApiUrl;

	@Autowired
	private String testPlansUrlTemplate;

	@Autowired
	private String testPointsUrlTemplate;

	@Autowired
	private String testRunUrlTemplate;

	@Autowired
	private String testResultIdUrlTemplate;

	@Autowired
	private String testRunResultsUrlTemplate;

	@Autowired
	private String runPayloadTemplate;

	@Autowired
	private String resultPayloadTemplate;

	/**
	 * Create url for testplan
	 * 
	 * @param organization
	 * @param project
	 * @param apiVersion
	 * @return
	 */
	public String buildTestPlansUrl(String organization, String project, String apiVersion) {
		return String.format(testPlansUrlTemplate, baseApiUrl, organization, project, apiVersion);
	}

	/**
	 * Create url for test point
	 * 
	 * @param organization
	 * @param project
	 * @param apiVersion
	 * @param testPlanId
	 * @param testSuiteId
	 * @param testCaseId
	 * @return
	 */
	public String buildTestPointsUrl(String organization, String project, int testPlanId, int testSuiteId,
			int testCaseId, String apiVersion) {
		return String.format(testPointsUrlTemplate, baseApiUrl, organization, project, testPlanId, testSuiteId,
				testCaseId, apiVersion);
	}

	/**
	 * Create url for testrun
	 * 
	 * @param organization
	 * @param project
	 * @param apiVersion
	 * @return
	 */
	public String buildTestRunUrl(String organization, String project, String apiVersion) {
		return String.format(testRunUrlTemplate, baseApiUrl, organization, project, apiVersion);
	}

	/**
	 * Create url for test result ids
	 * 
	 * @param organization
	 * @param project
	 * @param apiVersion
	 * @param testRunId
	 * @return
	 */
	public String buildTestResultIdsUrl(String organization, String project, String apiVersion, int testRunId) {
		return String.format(testResultIdUrlTemplate, baseApiUrl, organization, project, testRunId, apiVersion);
	}

	/**
	 * Create url for test result
	 * 
	 * @param organization
	 * @param project
	 * @param apiVersion
	 * @param runId
	 * @return
	 */
	public String buildTestResultsUrl(String organization, String project, int runId, String apiVersion) {
		return String.format(testRunResultsUrlTemplate, baseApiUrl, organization, project, runId, apiVersion);
	}

	/**
	 * Create payload for test run
	 * 
	 * @param runName
	 * @param testPlanId
	 * @param allTestPointIds
	 * @return
	 */
	public String createTestRunPayload(String runName, int testPlanId, List<Integer> allTestPointIds) {
		return String.format(runPayloadTemplate, runName, testPlanId, allTestPointIds);
	}

	/**
	 * Provide test result update
	 * 
	 * @param resultId
	 * @param outcomeString
	 * @return
	 */
	public String getResultUpdate(int resultId, String outcomeString) {
		return String.format(resultPayloadTemplate, resultId, outcomeString);
	}

}
