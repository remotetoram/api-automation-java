package com.synovus.mulesoft.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import com.synovus.mulesoft.apiobjects.SRequest;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Component
@Slf4j
public class EndpointReader {
    private String testEnv;
    private Map<String, String> propertiesMap = new HashMap<>();

    @Autowired
    public EndpointReader(@Value("${testEnv}") String testEnv,
                          @Value("classpath:endpoints.properties") Resource resource1,
                          @Value("classpath:endpointsAPIM.properties") Resource resource2) {
        this.testEnv = testEnv;
        log.info("TestEnv inside EndpointReader >> " + testEnv);
        if ("qaapim".equalsIgnoreCase(testEnv)) {
            loadProperties(resource2);
        } else {
            loadProperties(resource1);
        }
    }

    private void loadProperties(Resource resource) {
        try {
            Properties properties = new Properties();
            properties.load(resource.getInputStream());
            for (String key : properties.stringPropertyNames()) {
                propertiesMap.put(key, properties.getProperty(key));
            }
        } catch (IOException e) {
            log.error("Error loading properties", e);
        }
    }

    public String getEndpoint(String key) {
        return propertiesMap.get(key);
    }
}
