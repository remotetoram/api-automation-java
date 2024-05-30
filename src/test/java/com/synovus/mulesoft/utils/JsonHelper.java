package com.synovus.mulesoft.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.synovus.mulesoft.dbconnection.models.EmployeeTb;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JsonHelper {
	public boolean compareJsonStrings(String expectedJson, String actualJson) {
		try {
			JSONObject expectedObj = new JSONObject(expectedJson);
			JSONObject actualObj = new JSONObject(actualJson);

			if (expectedObj.toString().equals(actualObj.toString())) {
				return true;
			} else {
				return false;
			}
		} catch (JSONException e) {
			return false;
		}
	}
 
	 public  boolean fieldValueSearchinResponse(Response response, String fieldName, String fieldValue) {
	        if (response == null || fieldName == null || fieldValue == null) {
	            return false;
	        }

	        // Convert the response body to a JsonNode
	        JsonNode jsonNode = response.getBody().as(JsonNode.class);

	        // Call the findFieldValue method with the JsonNode
	        return findFieldValue(jsonNode, fieldName, fieldValue);
	    }

	    private  boolean findFieldValue(JsonNode jsonNode, String fieldName, String expectedValue) {
	        if (jsonNode == null || fieldName == null || expectedValue == null) {
	            return false;
	        }

	        // If the current node is an object, search its fields
	        if (jsonNode.isObject()) {
	            JsonNode fieldNode = jsonNode.get(fieldName);
	            if (fieldNode != null && fieldNode.asText().equals(expectedValue)) {
	                return true;
	            }
	            // If the field is not found at this level, recursively search child nodes
	            for (JsonNode child : jsonNode) {
	                if (findFieldValue(child, fieldName, expectedValue)) {
	                    return true;
	                }
	            }
	        }

	        // If the current node is an array, recursively search each element
	        else if (jsonNode.isArray()) {
	            for (JsonNode element : jsonNode) {
	                if (findFieldValue(element, fieldName, expectedValue)) {
	                    return true;
	                }
	            }
	        }

	        // If the current node is neither an object nor an array, return false
	        return false;
	    }
	

   
	public  List<String> getNamesFromResultList(List<EmployeeTb> resultList) {
		List<String> names = new ArrayList<>();
		ObjectMapper objectMapper = new ObjectMapper();
		ArrayNode jsonArray = objectMapper.valueToTree(resultList);
		for (JsonNode node : jsonArray) {
			names.add(node.get("name").asText());
		}
		return names;
	}
}
	
	


