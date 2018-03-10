package com.solutis.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.solutis.DTO.Mensagem;

@Service
public class FirebaseService {
	
	final String URL_FIREBASE = "https://heroicyberpunk.firebaseio.com/analise.json";
	
	public void enviarObjetoFirebase() {
			String targetUrl = URL_FIREBASE;

			RestTemplate restTemplate = new RestTemplate();
			Mensagem message = new Mensagem();
			message.setDescricao("AJHDASGFDHSAJKDSAJHGV");
			message.setId(1);
			restTemplate.postForLocation(targetUrl, message);
	}

}
