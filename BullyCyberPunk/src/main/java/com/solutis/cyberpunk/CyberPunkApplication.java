package com.solutis.cyberpunk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.solutis.controller")
public class CyberPunkApplication {
	public static void main(String[] args) {
		SpringApplication.run(CyberPunkApplication.class, args);
	}
}

