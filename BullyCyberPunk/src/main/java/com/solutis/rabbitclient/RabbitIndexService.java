package com.solutis.rabbitclient;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.solutis.DTO.MensagemUsuario;

@Component
public class RabbitIndexService {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	private String queue;

	private String exchange;

	public void sendMessage(MensagemUsuario mensagemUsuario) {
		rabbitTemplate.convertAndSend(exchange, queue, mensagemUsuario);
	}

	public void receiveMessage(Long idConflito) {
		rabbitTemplate.receiveAndConvert(queue, 100);
	}
}
