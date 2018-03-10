package com.solutis.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonObject;
import com.solutis.DTO.Mensagem;

@Service
public class FirebaseService {
	
	final String URL_FIREBASE = "https://heroicyberpunk.firebaseio.com/analise.json";
	
	public void enviarObjetoFirebase(String teste) {
			String targetUrl = URL_FIREBASE;

			RestTemplate restTemplate = new RestTemplate();
			Mensagem message = new Mensagem();
			message.setResposta(teste);
			message.setId(1);
			restTemplate.postForLocation(targetUrl, message);
	}

}
