package com.synovus.mulesoft.velocity;

import lombok.extern.slf4j.Slf4j;

import java.io.StringWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.app.event.implement.IncludeRelativePath;
import org.apache.velocity.runtime.RuntimeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import com.synovus.mulesoft.utils.TestDataConfig;

@Slf4j
@Component
public class JsonPayloadGenerator {
	@Autowired
	private TestDataConfig testDataConfig;

	/*
	 * Below method can be used if you want JSON payload generation by having
	 * specific VT file like for wrong data you need to create separate VT.
	 */
	public String generateJsonPayload(String templatePath, String propertiesFilePath) {
		Map<String, String> properties = testDataConfig.getLoadedProperties().get(propertiesFilePath);
		Map<String, String> basetestdata = testDataConfig.getLoadedProperties().get("src\\test\\resources\\testdata\\basedata.properties");

		if (properties == null) {
			System.err.println("Properties file " + propertiesFilePath + " not loaded.");
			return null;
		}

		// log.info("properties.entrySet(); " + properties.entrySet());

		try {
			String template = new String(Files.readAllBytes(Paths.get(templatePath)));
			log.info("Populating Base Test Data" + basetestdata.entrySet());
			for (Map.Entry<String, String> entry : basetestdata.entrySet()) {
				template = template.replace("$" + entry.getKey(), entry.getValue());
			}

			for (Map.Entry<String, String> entry : properties.entrySet()) {
				template = template.replace("$" + entry.getKey(), entry.getValue());
			}

			// System.out.println("Created JSON file: " + template);
			log.info("JSON after Processing " + template);
			return template;

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String generateJsonPayloadUsingAdditionalTestData(String templatePath, String propertiesFilePath) {
		Map<String, String> properties = testDataConfig.getLoadedProperties().get(propertiesFilePath);
		Map<String, String> basetestdata = testDataConfig.getLoadedProperties().get("src\\test\\resources\\testdata\\basedata.properties");
		if (properties == null) {
			System.err.println("Properties file " + propertiesFilePath + " not loaded.");
			return null;
		}

		log.info("properties.entrySet(); " + properties.entrySet());

		try {
			String template = new String(Files.readAllBytes(Paths.get(templatePath)));
			if (testDataConfig.additionalTestData != null) {
				for (Map.Entry<String, String> entry : testDataConfig.additionalTestData.entrySet()) {
					String key = entry.getKey();
					String value = entry.getValue();
					template = template.replace("$" + entry.getKey(), entry.getValue());
				}
			}
			log.info("Populating Base Test Data" + basetestdata.entrySet());
			for (Map.Entry<String, String> entry : basetestdata.entrySet()) {
				template = template.replace("$" + entry.getKey(), entry.getValue());
			}
			for (Map.Entry<String, String> entry : properties.entrySet()) {
				template = template.replace("$" + entry.getKey(), entry.getValue());
			}

			// System.out.println("Created JSON file: " + template);
			log.info("JSON after Processing " + template);
			return template;

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

//	public String generateJsonPayloadUsingModel(String template, Object object) {
//		log.info("Template engine started for " + template);
//		VelocityEngine velocityEngine = new VelocityEngine();
//		velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
//		velocityEngine.setProperty("classpath.resource.loader.class",
//				"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
//		velocityEngine.setProperty(RuntimeConstants.EVENTHANDLER_INCLUDE, IncludeRelativePath.class.getName());
//		velocityEngine.init();
//		Template t = velocityEngine.getTemplate("templates/" + template);
//		VelocityContext context = new VelocityContext();
//		context.put("data", object);
//		StringWriter writer = new StringWriter();
//		t.merge(context, writer);
//		log.info("JSON after Processing:" + writer.toString());
//		return writer.toString();
//	}

}
