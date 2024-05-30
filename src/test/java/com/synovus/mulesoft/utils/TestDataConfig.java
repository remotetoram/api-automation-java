package com.synovus.mulesoft.utils;

import org.springframework.stereotype.Component;
import java.io.IOException;
import javax.annotation.PostConstruct;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Component
public class TestDataConfig {

    private Map<String, Map<String, String>> loadedProperties;
    public Map<String, String> additionalTestData = new HashMap<>();
    
    public void addValueInAdditionalTestData(String key, String value) {
    	additionalTestData.put(key, value);
    }

    public String getValueFromAdditionalTestData(String key) {
        return additionalTestData.get(key);
    }

    @PostConstruct
    public void init() {
        loadedProperties = loadProperties();
    }

    private Map<String, Map<String, String>> loadProperties() {
        String folderPath = "src\\test\\resources\\testdata";
        Map<String, Map<String, String>> loadedProperties = new HashMap<>();
        try {
            Files.walk(Paths.get(folderPath))
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".properties"))
                    .forEach(path -> {
                        String filePath = path.toString();
                        Map<String, String> properties = new HashMap<>();
                        try {
                            Files.lines(Paths.get(filePath))
                                    .forEach(line -> {
                                        String[] parts = line.split("=", 2);
                                        if (parts.length == 2) {
                                            properties.put(parts[0], parts[1]);
                                        } else {
                                            System.err.println("Invalid line format in file " + filePath + ": " + line);
                                        }
                                    });
                            loadedProperties.put(filePath, properties);
                            System.out.println("Loaded properties from file: " + filePath);
                        } catch (IOException e) {
                            System.err.println("Error loading properties from file: " + filePath);
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loadedProperties;
    }

    public Map<String, Map<String, String>> getLoadedProperties() {
        return loadedProperties;
    }
  
}
