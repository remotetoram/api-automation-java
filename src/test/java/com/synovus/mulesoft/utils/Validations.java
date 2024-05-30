package com.synovus.mulesoft.utils;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static io.restassured.module.jsv.JsonSchemaValidatorSettings.settings;
import static io.restassured.matcher.RestAssuredMatchers.matchesXsd;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;
import io.restassured.path.json.JsonPath;
import java.util.Map;

@Slf4j
@Component
public class Validations {
	@Autowired
	private TestDataConfig testDataConfig;
	@Autowired
	private SchemaHelper schemaHelper;
	@Autowired
	private JsonHelper jsonHelper;

	public String getFieldValueFromPropertiesFIle(String key, String propertiesFileName) {
		String propertiesFilePath = "src\\test\\resources\\testdata\\" + propertiesFileName + ".properties";
		Map<String, String> properties = testDataConfig.getLoadedProperties().get(propertiesFilePath);
		if (properties == null) {
			System.err.println("Properties file " + propertiesFilePath + " not loaded.");
			return null;
		}
		return properties.get(key).toString();
	}

	public void ResponseCodeValidator(int expectedStatusCode, Response response) {
		log.info("Response code:>> " + response.getStatusCode());
		Assert.assertEquals("Response Code '" + response.getStatusCode() + "' recieved.", expectedStatusCode,response.getStatusCode());
		
	}

	public boolean fieldValueValidatorinResponse(String fieldName, String fieldValue,Response response) {
		if (response == null || fieldName == null || fieldValue == null) {
			return false;
		}
		String responseBody = response.getBody().asString();
		return responseBody.contains("\"" + fieldName + "\":\"" + fieldValue + "\"");
	}

	public void fieldValueValidatorinResponseAdv(String fieldName, String fieldValue, Response response) {
		boolean result = jsonHelper.fieldValueSearchinResponse(response, fieldName, fieldValue);
		log.info("FieldFound:>> " + result);
		Assert.assertTrue(result);
	}

	public void ResponseCodeValidator(int expectedStatusCode, List<Integer> returnCode) {
		for (int i = 0; i < returnCode.size(); i++) {
			Assert.assertEquals(expectedStatusCode, returnCode.get(i).intValue());
		}
	}

	public void ResponseRestSchemaValidator(String schemafileName, Response response) {
		response.then().assertThat().body(matchesJsonSchema(schemaHelper.getSchemaFilePath(schemafileName + ".json"))
				.using(settings().with().checkedValidation(false)));
	}

	public void ResponseSoapSchemaValidator(String schemafileName, Response response) throws Exception {
		String bodyContent = schemaHelper.parseAndModifySOAPMessage(response);
		//log.info("bodyContent=" + bodyContent);
		assertThat("Soap response matches with contract", bodyContent,
				matchesXsd(schemaHelper.getXmlSchemaFilePath(schemafileName)));
	}
     //Use for simple json response
	public void validateFieldValue(String fieldName, String expectedValue, Response response) {
		String actualValue = response.jsonPath().getString(fieldName);
		log.info("actualValue=" + actualValue);
		Assert.assertEquals("Field'" + fieldName + "' value is incorrect", expectedValue, actualValue);
	}

	public boolean isErrorMessagePresent(Response response, String errorMessage) {
		// Convert Response to JsonPath
		JsonPath jsonPath = response.jsonPath();
		// Traverse the JSON structure and search for the error message
		return searchErrorMessage(jsonPath.get(), errorMessage);
	}

	private static boolean searchErrorMessage(Object obj, String errorMessage) {
		if (obj instanceof String) {
			// Base case: If the current object is a String, compare it with the error
			// message
			return errorMessage.equals(obj);
		} else if (obj instanceof List) {
			// If the current object is a list, iterate over its elements
			for (Object element : (List<?>) obj) {
				if (searchErrorMessage(element, errorMessage)) {
					return true;
				}
			}
		} else if (obj instanceof Map) {
			// If the current object is a map, traverse its key-value pairs
			for (Map.Entry<?, ?> entry : ((Map<?, ?>) obj).entrySet()) {
				if (searchErrorMessage(entry.getValue(), errorMessage)) {
					return true;
				}
			}
		}
		return false;
	}

	public void ResponseErrorValidator(Response response, String errorMessage) {
		assertTrue(isErrorMessagePresent(response, errorMessage));
	}

	public void validateDateFieldValue(String fieldName, Response response) {
		String actualValue = response.jsonPath().getString(fieldName);
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			dateFormat.parse(actualValue);
		} catch (ParseException e) {
			Assert.fail("Field '" + fieldName + "' with value '" + actualValue + "' is not in yyyy-MM-dd format");
		}
	}

	public void validateIntegerFieldValue(String fieldName, Response response) {
		String actualValue = response.jsonPath().getString(fieldName);
		try {
			Integer.parseInt(actualValue);
		} catch (NumberFormatException e) {
			Assert.fail("Field '" + fieldName + "' with value '" + actualValue + "' is not an integer");
		}
	}

	public void validateStringFieldValue(String fieldName, Response response) {
		Object actualValue = response.jsonPath().get(fieldName);
		if (!(actualValue instanceof String)) {
			Assert.fail("Field '" + fieldName + "' with value '" + actualValue + "' is not an string");
		}
	}

	public void validateLongFieldValue(String fieldName, Response response) {
		String actualValue = response.jsonPath().getString(fieldName);
		try {
			Long.parseLong(actualValue);
		} catch (NumberFormatException e) {
			Assert.fail("Field '" + fieldName + "' with value '" + actualValue + "' is not a valid long");
		}
	}
}
