package com.solutis.DTO;

import com.google.gson.JsonObject;
import com.ibm.watson.developer_cloud.conversation.v1.model.Context;

public class Mensagem {
	
	private int id;
	private String descricao;
	private Context context;
	private String resposta;
	public String getResposta() {
		return resposta;
	}
	public void setResposta(String resposta) {
		this.resposta = resposta;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Context getContext() {
		return context;
	}
	public void setContext(Context context) {
		this.context = context;
	}

}
