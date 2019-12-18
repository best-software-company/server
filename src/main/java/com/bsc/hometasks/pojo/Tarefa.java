package com.bsc.hometasks.pojo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Tarefa {
	private  int idTarefa;
	private String nome;
	private String descricao;
	private String idResponsavel;
	private String idRelator;
	private String estado;
	private String data;
	private float valor;
	private boolean repasse;

	public Tarefa(int idTarefa, String nome, String descricao, String idResponsavel, String idRelator, String estado, String data, float valor, boolean repasse) {
		this.idTarefa = idTarefa;
		this.nome = nome;
		this.descricao = descricao;
		this.idResponsavel = idResponsavel;
		this.idRelator = idRelator;
		this.estado = estado;
		this.data = data;
		this.valor = valor;
		this.repasse = repasse;
	}

    public Tarefa() {
    }

    public Tarefa(String nome, String descricao, String idResponsavel, String idRelator, String estado, String data, float valor) {
		this.nome = nome;
		this.descricao = descricao;
		this.idResponsavel = idResponsavel;
		this.idRelator = idRelator;
		this.estado = estado;
		this.data = data;
		this.valor = valor;
	}

	public boolean isRepasse() {
		return repasse;
	}

	public void setRepasse(boolean repasse) {
		this.repasse = repasse;
	}

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

	public int getIdTarefa() {
		return idTarefa;
	}

	public void setIdTarefa(int idTarefa) {
		this.idTarefa = idTarefa;
	}

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

	public String getIdResponsavel() {
		return idResponsavel;
	}

	public void setIdResponsavel(String idResponsavel) {
		this.idResponsavel = idResponsavel;
	}

	public String getIdRelator() {
		return idRelator;
	}

	public void setIdRelator(String idRelator) {
		this.idRelator = idRelator;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Tarefa{" +
				"idTarefa=" + idTarefa +
				", nome='" + nome + '\'' +
				", descricao='" + descricao + '\'' +
				", idResponsavel='" + idResponsavel + '\'' +
				", idRelator='" + idRelator + '\'' +
				", estado='" + estado + '\'' +
				", data='" + data + '\'' +
				", valor=" + valor +
				'}';
	}
}
