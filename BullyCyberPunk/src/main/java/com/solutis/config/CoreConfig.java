package com.solutis.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(ignoreResourceNotFound = false, value = "classpath:core-application.properties")
@PropertySource(ignoreResourceNotFound = true, value = "classpath:core-application-${spring.profiles.active}.properties")
public class CoreConfig {

}
