package com.bptn.feedAppAutomation.cucumberRunners;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("com/bptn/feedAppAutomation/pageFeatures/registerPage.feature")
@SelectClasspathResource("com/bptn/feedAppAutomation/pageFeatures/loginPage.feature")
@SelectClasspathResource("com/bptn/feedAppAutomation/pageFeatures/feedPage.feature")

@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.bptn.feedAppAutomation")
public class RunCucumberTest {
}
