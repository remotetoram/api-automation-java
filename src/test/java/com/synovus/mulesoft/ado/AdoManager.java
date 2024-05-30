package com.synovus.mulesoft.ado;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.synovus.mulesoft.ado.HttpUtils.HttpMethod;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AdoManager {

	@Autowired
	private AzureDevOpsUrlBuilder azureDevOpsUrlBuilder;
	private String organization;
	private String project;
	private String apiVersion;
	private HttpUtils httpUtils;

	public AdoManager() {

	}

	public AdoManager(String organization, String project, String apiVersion) {
		this.organization = organization;
		this.project = project;
		this.apiVersion = apiVersion;
	}

	/**
	 * create base request
	 * 
	 * @return
	 */
	private HttpRequest createBaseRequest() {
		HttpRequest request = new HttpRequest();
		request.setPesronalAccessToken(pat);
		return request;
	}

	/**
	 * It provide testplan id
	 * 
	 * @param planName
	 * @return
	 * @throws IOException
	 */
	public int getTestPlanId(String planName) throws IOException {

		if (azureDevOpsUrlBuilder == null) {
			azureDevOpsUrlBuilder = new AzureDevOpsUrlBuilder();
		}
		if (httpUtils == null) {
			httpUtils = new HttpUtils();
		}
		String url = azureDevOpsUrlBuilder.buildTestPlansUrl(organization, project, apiVersion);
		HttpRequest getRequest = createBaseRequest();
		getRequest.setUrl(url);
		getRequest.setMethod(HttpMethod.GET);
		String response = httpUtils.executeHttpGet(getRequest);
		return extractIdFromJson(response, "value", "name", planName);
	}

	/**
	 * It is providing test point id
	 * 
	 * @param testPlanId
	 * @param testSuiteId
	 * @param testCaseId
	 * @return
	 * @throws IOException
	 */
	public int getTestPoint(int testPlanId, int testSuiteId, int testCaseId) throws IOException {

		if (azureDevOpsUrlBuilder == null) {
			azureDevOpsUrlBuilder = new AzureDevOpsUrlBuilder();
		}
		if (httpUtils == null) {
			httpUtils = new HttpUtils();
		}
		String url = azureDevOpsUrlBuilder.buildTestPointsUrl(organization, project, testPlanId, testSuiteId,
				testCaseId, apiVersion);
		HttpRequest getRequest = createBaseRequest();

		getRequest.setUrl(url);
		getRequest.setMethod(HttpMethod.GET);
		String response = httpUtils.executeHttpGet(getRequest);
		log.info(url);
		log.info(httpUtils.executeHttpGet(getRequest));
		int testPointId = extractIdFromJson(response, "value", "id");
		return testPointId;
	}

	/**
	 * Testrun is created here
	 * 
	 * @param runName
	 * @param testPlanId
	 * @param testPointIds
	 * @return
	 * @throws IOException
	 */
	public int createTestRun(String runName, int testPlanId, List<Integer> allTestPointIds) throws IOException {

		if (azureDevOpsUrlBuilder == null) {
			azureDevOpsUrlBuilder = new AzureDevOpsUrlBuilder();
		}
		if (httpUtils == null) {
			httpUtils = new HttpUtils();
		}
		String url = azureDevOpsUrlBuilder.buildTestRunUrl(organization, project, apiVersion);
		String payload = azureDevOpsUrlBuilder.createTestRunPayload(runName, testPlanId, allTestPointIds);
		HttpRequest postRequest = createBaseRequest();
		postRequest.setUrl(url);
		postRequest.setPayload(payload);
		postRequest.setMethod(HttpMethod.POST);
		String response = httpUtils.executeHttpPost(postRequest);
		return extractIdFromJson(response, "id");
	}

	/**
	 * It is providing test result ids
	 * 
	 * @param testRunId
	 * @return
	 * @throws IOException
	 */
	public List<Integer> getTestResultIds(int testRunId) throws IOException {
		if (azureDevOpsUrlBuilder == null) {
			azureDevOpsUrlBuilder = new AzureDevOpsUrlBuilder();
		}
		if (httpUtils == null) {
			httpUtils = new HttpUtils();
		}
		String url = azureDevOpsUrlBuilder.buildTestResultIdsUrl(organization, project, apiVersion, testRunId);
		HttpRequest getRequest = createBaseRequest();
		getRequest.setUrl(url);
		getRequest.setMethod(HttpMethod.GET);
		String response = httpUtils.executeHttpGet(getRequest);
		return extractIdsFromJson(response, "value", "id");
	}

	/**
	 * It is providing test results on the basis of test case outcome
	 * 
	 * @param runId
	 * @param resultIds
	 * @param outcomes
	 * @throws IOException
	 */
	public void updateTestResults(int runId, List<Integer> resultIds, List<Boolean> outcomes) throws IOException {

		if (azureDevOpsUrlBuilder == null) {
			azureDevOpsUrlBuilder = new AzureDevOpsUrlBuilder();
		}
		if (httpUtils == null) {
			httpUtils = new HttpUtils();
		}
		String url = azureDevOpsUrlBuilder.buildTestResultsUrl(organization, project, runId, apiVersion);
		String payload = generateUpdatePayload(resultIds, outcomes);
		HttpRequest patchRequest = createBaseRequest();
		patchRequest.setUrl(url);
		patchRequest.setPayload(payload);
		patchRequest.setMethod(HttpMethod.PATCH);
		httpUtils.executeHttpPatch(patchRequest);
	}

	/**
	 * Generating payload on the basis of test execution
	 * 
	 * @param resultIds
	 * @param outcomes
	 * @return
	 */
	private String generateUpdatePayload(List<Integer> resultIds, List<Boolean> outcomes) {
		StringBuilder payloadBuilder = new StringBuilder("[");
		for (int i = 0; i < resultIds.size(); i++) {
			int resultId = resultIds.get(i);
			boolean outcome = outcomes.get(i);
			String outcomeString = outcome ? "PASSED" : "FAILED";
			String resultUpdate = azureDevOpsUrlBuilder.getResultUpdate(resultId, outcomeString);
			payloadBuilder.append(resultUpdate);
			if (i < resultIds.size() - 1) {
				payloadBuilder.append(",");
			}
		}
		payloadBuilder.append("]");

		return payloadBuilder.toString();
	}

	/**
	 * extracting testpoint id on the basis of json
	 * 
	 * @param json
	 * @param arrayName
	 * @param idFieldName
	 * @return
	 * @throws IOException
	 */
	private int extractIdFromJson(String json, String arrayName, String idFieldName) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode = objectMapper.readTree(json);

		JsonNode arrayNode = rootNode.path(arrayName);
		if (arrayNode.isArray()) {
			for (JsonNode node : arrayNode) {
				int id = node.path(idFieldName).asInt();
				if (id != 0) {
					return id;
				}
			}
		}

		throw new IllegalStateException("ID not found in the JSON response.");
	}

	/**
	 * extracting testrun id on the basis of json
	 * 
	 * @param json
	 * @param fieldName
	 * @return
	 * @throws IOException
	 */
	private int extractIdFromJson(String json, String fieldName) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode = objectMapper.readTree(json);

		JsonNode idNode = rootNode.path(fieldName);
		if (!idNode.isMissingNode() && idNode.isNumber()) {
			return idNode.asInt();
		}

		throw new IllegalStateException("ID not found in the JSON response.");
	}

	/**
	 * extracting testplan id on the basis of json
	 * 
	 * @param json
	 * @param arrayName
	 * @param fieldName
	 * @param fieldValue
	 * @return
	 * @throws IOException
	 */
	private int extractIdFromJson(String json, String arrayName, String fieldName, String fieldValue)
			throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode = objectMapper.readTree(json);

		JsonNode arrayNode = rootNode.path(arrayName);
		if (arrayNode.isArray()) {
			for (JsonNode node : arrayNode) {
				String value = node.path(fieldName).asText();

				if (value.equals(fieldValue)) {
					return node.path("id").asInt();
				}
			}
		}

		throw new IllegalStateException("ID not found in the JSON response.");
	}

	/**
	 * extracting test result ids on the basis of json
	 * 
	 * @param json
	 * @param arrayName
	 * @param idFieldName
	 * @return
	 * @throws IOException
	 */
	private List<Integer> extractIdsFromJson(String json, String arrayName, String idFieldName) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode = objectMapper.readTree(json);

		JsonNode arrayNode = rootNode.path(arrayName);
		if (arrayNode.isArray()) {
			return StreamSupport.stream(arrayNode.spliterator(), false).map(node -> node.path(idFieldName).asInt())
					.collect(Collectors.toList());
		}

		throw new IllegalStateException("IDs not found in the JSON response.");
	}

}
