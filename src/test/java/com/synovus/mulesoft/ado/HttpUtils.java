package com.synovus.mulesoft.ado;

import java.io.IOException;
import java.util.Base64;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpUtils {

	public enum HttpMethod {
		GET, POST, PATCH
	}

	
	/**
	 * enable http request on the basis of enum
	 * @param httpRequest
	 * @return
	 * @throws IOException
	 */
	public String executeHttpRequest(HttpRequest httpRequest) throws IOException {
		HttpClient client = HttpClients.createDefault();
		HttpRequestBase request;

		switch (httpRequest.getMethod()) {
		case GET:
			request = new HttpGet(httpRequest.getUrl());
			break;
		case POST:
			HttpPost postRequest = new HttpPost(httpRequest.getUrl());
			if (httpRequest.getPayload() != null) {
				postRequest.setEntity(new StringEntity(httpRequest.getPayload()));
			}
			request = postRequest;
			break;
		case PATCH:
			HttpPatch patchRequest = new HttpPatch(httpRequest.getUrl());
			if (httpRequest.getPayload() != null) {
				patchRequest.setEntity(new StringEntity(httpRequest.getPayload()));
			}
			request = patchRequest;
			break;
		default:
			throw new IllegalArgumentException("Unsupported HTTP method: " + httpRequest.getMethod());
		}

		String authHeader = createAuthorizationHeader(httpRequest.getPesronalAccessToken());
		request.addHeader("Authorization", authHeader);

		if (httpRequest.getMethod() == HttpMethod.POST || httpRequest.getMethod() == HttpMethod.PATCH) {
			request.addHeader("Content-Type", "application/json");
		}

		HttpResponse response = client.execute(request);
		return EntityUtils.toString(response.getEntity());
	}

	/**
	 * create header for authorization
	 * 
	 * @param pesronalAccessToken
	 * @return
	 */
	private String createAuthorizationHeader(String pesronalAccessToken) {
		return "Basic " + Base64.getEncoder().encodeToString((":" + pesronalAccessToken).getBytes());
	}

	
	/**It enable get request
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public String executeHttpGet(HttpRequest request) throws IOException {
		
		return executeHttpRequest(request);
	}

	
	/**It enable post request
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public String executeHttpPost(HttpRequest request) throws IOException {
		return executeHttpRequest(request);
	}

	
	/** It enable patch request
	 * @param request
	 * @throws IOException
	 */
	public void executeHttpPatch(HttpRequest request) throws IOException {
		executeHttpRequest(request);
	}

}
