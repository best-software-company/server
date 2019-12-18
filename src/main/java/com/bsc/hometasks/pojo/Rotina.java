package com.bsc.hometasks.pojo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Rotina {
	int idRotina;
	String validade;
	boolean alternar;
//	Tarefa tarefa;
	String nome;
	String descricao;
	String idUsuario;

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Rotina() {
	}

	public int getIdRotina() {
		return idRotina;
	}

	public void setIdRotina(int idRotina) {
		this.idRotina = idRotina;
	}

	public Rotina(String validade, boolean alternar, int idRotina, String nome, String descricao, String idUsuario) {
		this.validade = validade;
		this.alternar = alternar;
		this.idRotina = idRotina;
		this.nome = nome;
		this.descricao = descricao;
		this.idUsuario = idUsuario;
		//this.tarefa = tarefa;
	}

	public String getValidade() {
		return validade;
	}

	public void setValidade(String validade) {
		this.validade = validade;
	}

	public boolean isAlternar() {
		return alternar;
	}

	public void setAlternar(boolean alternar) {
		this.alternar = alternar;
	}

//	//public Tarefa getTarefa() {
//		return tarefa;
//	}

//	public void setTarefa(Tarefa tarefa) {
//		this.tarefa = tarefa;
//	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return "Rotina{" +
				"validade='" + validade + '\'' +
				", alternar=" + alternar +
				'}';
	}
}
