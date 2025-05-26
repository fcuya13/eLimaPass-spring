package com.francocuya13.elimapassspring.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Configuration
public class ApiConfig {

    @RequestMapping("/elimapass/v1")
    @RestController
    public static class BaseController {

    }
}
