package com.synovus.mulesoft.steps;

import org.springframework.beans.factory.annotation.Autowired;
import org.junit.Assert;
import com.synovus.mulesoft.utils.Token;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class AuthenticationValidationSteps {

	   @Autowired
	    private Token token;
	    private String apiToken;
	    
	    @Given("user gets the oauth token")
	    public void userGetsTheToken() {
	        apiToken = token.getAuthToken();        
	    }

	    @Then("validate that oauth token is generated")
	    public void oAuthTokenIsGenerated() {
	        log.info(apiToken);
	        Assert.assertNotNull(apiToken);
	    }
}
