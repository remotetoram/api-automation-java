package com.synovus.mulesoft.runners;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.cucumber.java.Before;
import io.cucumber.spring.CucumberContextConfiguration;

@RunWith(SpringRunner.class)
@CucumberContextConfiguration
@SpringBootTest
public class SpringBootRunner {

	@Before
	public void setupCucumberSpringContext() {

	}
}
