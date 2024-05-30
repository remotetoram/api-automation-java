package com.synovus.mulesoft.ado;

import com.synovus.mulesoft.ado.HttpUtils.HttpMethod;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HttpRequest {

	private String url;
	private HttpMethod method;
	private String payload;
	private String pesronalAccessToken;

}
