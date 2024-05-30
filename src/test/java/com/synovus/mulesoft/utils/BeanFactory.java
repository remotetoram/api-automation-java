package com.synovus.mulesoft.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import com.github.javafaker.Faker;
import com.synovus.mulesoft.ado.AdoManager;
import com.synovus.mulesoft.runners.Hook;

@Configuration
public class BeanFactory {

	@Lazy
	@Autowired
	private Hook hooks;

	@Value("${synovus.ado.organization}")
	private String organization;

	@Value("${synovus.ado.project}")
	private String project;

	@Value("${synovus.ado.apiVersion}")
	private String apiVersion;

	@Bean
	public AdoManager getAdoManager() {
		return new AdoManager(organization, project, apiVersion);
	}

	@Bean
	public Faker getfaker() {
		return new Faker();
	}

}
