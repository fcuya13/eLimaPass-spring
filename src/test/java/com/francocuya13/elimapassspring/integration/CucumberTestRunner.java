package com.francocuya13.elimapassspring.integration;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.core.options.Constants.PLUGIN_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty, html:target/cucumber-reports/cucumber.html, json:target/cucumber-reports/cucumber.json")
public class CucumberTestRunner {
    // Clase vacia que ejecuta los escenarios definidos en Cucumber.
    // Los archivos .feature ubicados en el directorio "features" son utilizados como entrada.
    // Los reportes de prueba se generan en los formatos HTML y JSON en el directorio especificado.
}