package com.solutis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.watson.developer_cloud.conversation.v1.model.Context;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;
import com.solutis.DTO.Mensagem;
import com.solutis.DTO.MensagemUsuario;
import com.solutis.rabbitclient.RabbitIndexService;
import com.solutis.service.FirebaseService;
import com.solutis.service.WatsonService;

@RestController
@RequestMapping("mensagem")
@Component
public class MessageController {

	private static Context contexto = null;
	private static String mensagemCompleta = null;
	
	protected MultiValueMap<String, String> getHTTPHeader(){
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Access-Control-AllowedFor-Origin", "*");
		headers.add("Access-Control-AllowedFor-Headers", "Origin, X-Requested-With, Content-Type, Accept");

		return headers;
	}
	
	@Autowired
	WatsonService servico;
	
	@Autowired
	FirebaseService servicoFire;
	
	@Autowired
	RabbitIndexService servicoRabbit;
	
	@Autowired
	WatsonController watsonController;
	
	@PostMapping
	
	public ResponseEntity<?> receiveMessage(@RequestBody MensagemUsuario message) {
		try {
		MessageResponse resposta = servico.enviarMensagem(message.getDescricao(), contexto);
		MensagemUsuario mensagemUsuario = new MensagemUsuario();
		mensagemCompleta += " " + message.getDescricao();
		this.contexto = resposta.getContext();
		mensagemUsuario.setIdUsuario(2);
		mensagemUsuario.setDescricao(resposta.getOutput().getText().get(0));
		if (mensagemCompleta.contains("Tchau")) {
			watsonController.analyzeText(mensagemCompleta);
		}
//		servicoRabbit.sendMessage(mensagemUsuario);
		return new ResponseEntity<MensagemUsuario>(mensagemUsuario, getHTTPHeader(), HttpStatus.CREATED);
		} catch (Exception e) {
		return new ResponseEntity<String>("A mensagem falhou!", getHTTPHeader(), HttpStatus.CREATED);	
		}
	}

}
