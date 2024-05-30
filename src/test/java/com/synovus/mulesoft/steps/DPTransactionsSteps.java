package com.synovus.mulesoft.steps;

import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.synovus.mulesoft.utils.*;
import com.synovus.mulesoft.apiobjects.ResponseHolder;
import com.synovus.mulesoft.apiobjects.SRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DPTransactionsSteps {
	@Autowired
	private SRequest sRequest;
	@Autowired
	private ResponseHolder responseHolder;
	@Autowired
	private Validations validations;
	String resourceEndpoint;
	String soapEndpoint;
	String[] detail;
	List<String> successMsg = new LinkedList<>();
	List<Integer> returnCode = new LinkedList<>();
	

	
}