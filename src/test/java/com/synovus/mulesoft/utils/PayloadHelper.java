package com.synovus.mulesoft.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.synovus.mulesoft.velocity.JsonPayloadGenerator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PayloadHelper {
	@Autowired
	private JsonPayloadGenerator jsonPayloadGenerator;

	public static String getPayloadFile(String filePath) throws IOException {
		log.info("Reading the Payload file: " + filePath);
		return new String(Files.readAllBytes(Paths.get("src/test/resources/payloads/" + filePath)));
	}

	public String createPayload(String velocityFileName, String propertiesFileName) throws IOException {

		String velocityFilePath = "src/test/resources/templates/" + velocityFileName;
		String propertiesFilePath = "src\\test\\resources\\testdata\\" + propertiesFileName + ".properties";
		return jsonPayloadGenerator.generateJsonPayload(velocityFilePath, propertiesFilePath);

	}

	public String createPayloadWithAddtionalTestData(String velocityFileName, String propertiesFileName)
			throws IOException {

		String velocityFilePath = "src/test/resources/templates/" + velocityFileName;
		String propertiesFilePath = "src\\test\\resources\\testdata\\" + propertiesFileName + ".properties";
		return jsonPayloadGenerator.generateJsonPayloadUsingAdditionalTestData(velocityFilePath, propertiesFilePath);

	}

	public String convertXmlToString(String xmlFilePath) throws IOException {
		log.info("Reading the Payload file: " + xmlFilePath);
		return new String(Files.readAllBytes(Paths.get("src/test/resources/payloads/xml/" + xmlFilePath)));
	}

}
