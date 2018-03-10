package com.solutis.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solutis.DTO.Mensagem;

@RestController
@RequestMapping("mensagem")
@Component
public class MessageController {
	
	protected MultiValueMap<String, String> getHTTPHeader(){
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Access-Control-AllowedFor-Origin", "*");
		headers.add("Access-Control-AllowedFor-Headers", "Origin, X-Requested-With, Content-Type, Accept");

		return headers;
	}
	
	@PostMapping
	public ResponseEntity<?> receiveMessage(@RequestBody Mensagem message) {
		
	return new ResponseEntity<String>("Sucesso", getHTTPHeader(), HttpStatus.CREATED);
	}

}
