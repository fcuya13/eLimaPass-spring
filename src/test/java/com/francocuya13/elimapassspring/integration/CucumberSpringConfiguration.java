package com.francocuya13.elimapassspring.integration;

import com.francocuya13.elimapassspring.ELimaPassSpringApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(classes = ELimaPassSpringApplication.class)
public class CucumberSpringConfiguration {
}
