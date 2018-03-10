package com.solutis.cyberpunk;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.solutis.service.WatsonService;

@SpringBootApplication
@ComponentScan("com.solutis.controller")
@ComponentScan("com.solutis.service")
public class CyberPunkApplication {
	
	@Autowired
	WatsonService service;
	
	public static void main(String[] args) {
		SpringApplication.run(CyberPunkApplication.class, args);
	}
	
	public void rodar() {
		service.enviarMensagem("teste", null);
	}
	
	@PostConstruct
	public void started() {
		rodar();
	}
}

