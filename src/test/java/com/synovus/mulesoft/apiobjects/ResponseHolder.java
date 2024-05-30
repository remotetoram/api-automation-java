package com.synovus.mulesoft.apiobjects;

import io.restassured.response.Response;
import org.springframework.stereotype.Component;

@Component
public class ResponseHolder {

    private Response response;

    public void setResponse(Response response) {
        this.response = response;
    }

    public Response getResponse() {
        return response;
    }
}
