package com.synovus.mulesoft.runners;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import com.synovus.mulesoft.ado.AdoManager;
import com.synovus.mulesoft.utils.BeanFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Hook {
	@Value("${synovus.ado.update}")
	private boolean adoUpdate;
	@Autowired
	private AdoManager adoManager;
	private int testPlanId;
	private List<Boolean> scenarioOutcomes = new ArrayList<>();
	private List<Integer> testPointsIds = new ArrayList<>();
	private Set<String> encounteredTags = new HashSet<>();
	@Autowired
	private BeanFactory beanFactory;

	@PostConstruct
	public void initialize() {
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			try {
				testPlanId = 7019;

				int createTestRunNew = adoManager.createTestRun(beanFactory.getfaker().app().name(), testPlanId,
						testPointsIds);
				log.info("testRun" + createTestRunNew);

				List<Integer> testResultIds = adoManager.getTestResultIds(createTestRunNew);
				log.info("testResultIds" + testResultIds);

				adoManager.updateTestResults(createTestRunNew, testResultIds, scenarioOutcomes);
			} catch (IOException e) {
				log.info(e.getMessage());
			}

			if (isTokenExpired()) {
				log.info("Token Expiry Validation is a ToDo!");
			}
		}));
	}

	private boolean isTokenExpired() {
		return true;
	}

	@Before(order = 1)
	public void logBeforeScenario(final Scenario scenario) {
		log.debug(StringUtils.rightPad("Starting scenario:", 20) + "[{}] - [{}]", getFeatureName(scenario),
				scenario.getName());
	}

	@After(order = Integer.MAX_VALUE)
	public void logAfterScenario(final Scenario scenario) {
		if (adoUpdate) {
			adoManager = beanFactory.getAdoManager();
			String suiteId = getFeatureFilePath(scenario).replace("@ts", "").trim();
			String testCaseTag = null;
			for (String tag : scenario.getSourceTagNames()) {
				tag = tag.toLowerCase();
				if (tag.startsWith("@tc") && !encounteredTags.contains(tag)) {
					testCaseTag = tag;
					encounteredTags.add(tag);
					break;
				}
			}
			testCaseTag = testCaseTag.replace("@tc", "");

			int testCaseId = Integer.parseInt(testCaseTag);
			int testSuiteId = Integer.parseInt(suiteId);

			boolean scenarioOutcome = !scenario.isFailed(); // true if passed, false if failed
			scenarioOutcomes.add(scenarioOutcome);
			log.info("scenarioOutcome: " + scenarioOutcome);

			try {
				testPlanId = 7019;
				int testPoint = adoManager.getTestPoint(testPlanId, testSuiteId, testCaseId);
				log.info("Test case id: " + testCaseId + ", Test point result: " + testPoint);
				testPointsIds.add(testPoint);
			} catch (IOException e) {
				log.error("Error processing ADO test point: " + e.getMessage());
			}
		}
		log.debug(StringUtils.rightPad("Finished scenario:", 20) + "[{}] - [{}] [{}]", getFeatureName(scenario),
				scenario.getName(), scenario.getStatus());
	}

	private String getFeatureName(Scenario scenario) {
		String featureName = scenario.getId();
		featureName = StringUtils.substringBeforeLast(featureName, ".feature");
		featureName = StringUtils.substringAfterLast(featureName, "/");
		return featureName;
	}

	private String getFeatureFilePath(Scenario scenario) {

		try {
			log.info("suitetag: " + scenario.getSourceTagNames().iterator().next());
			return scenario.getSourceTagNames().iterator().next();
		} catch (NoSuchElementException e) {
			log.error("Failed to extract feature file path from scenario source.", e);
			return "NoFeatureId";
		}
	}
}