package com.solutis.service;


import org.springframework.stereotype.Service;

import com.ibm.watson.developer_cloud.conversation.v1.Conversation;
import com.ibm.watson.developer_cloud.conversation.v1.model.Context;
import com.ibm.watson.developer_cloud.conversation.v1.model.InputData;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageOptions;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;

@Service
public class WatsonService {
	
	public MessageResponse enviarMensagem(String mensagem, Context context) {
		Conversation service = new Conversation(Conversation.VERSION_DATE_2017_05_26);
		service.setUsernameAndPassword("b1b65dd6-9ea4-4972-a76e-fb7817d68807", "4AXOHPl8CdLQ"); 

		String workspaceId = "dfedc5b2-fb15-408f-bc6e-9a16d4783c5e";

		InputData input = new InputData.Builder(mensagem).build();

		MessageOptions options = new MessageOptions.Builder(workspaceId)
		  .input(input)
		  .context(context)
		  .build();

		MessageResponse response = service.message(options).execute();
		//ENVIAR O CONTEXT PRO FRONT END
		return response;	
		}

}
