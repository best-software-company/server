package com.bsc.hometasks.pojo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Regra {
	private int idRegra;
	private String nome;
	private String descricao;
	private boolean estado;
	private String idUsuario;
	private int idCasa;
	private String data;
	private int valor;

	public Regra() {
	}

	public Regra(int idRegra, String nome, String descricao, boolean estado, String idUsuario, int idCasa, String data, int valor) {
		this.idRegra = idRegra;
		this.nome = nome;
		this.descricao = descricao;
		this.estado = estado;
		this.idUsuario = idUsuario;
		this.idCasa = idCasa;
		this.data = data;
		this.valor = valor;
	}

	public Regra(String nome, String descricao, boolean estado, String idUsuario, int idCasa, String data, int valor) {
		this.nome = nome;
		this.descricao = descricao;
		this.estado = estado;
		this.idUsuario = idUsuario;
		this.idCasa = idCasa;
		this.data = data;
		this.valor = valor;
	}


	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	public int getIdRegra() {
		return idRegra;
	}

	public void setIdRegra(int idRegra) {
		this.idRegra = idRegra;
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

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public int getIdCasa() {
		return idCasa;
	}

	public void setIdCasa(int idCasa) {
		this.idCasa = idCasa;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Regra{" +
				"idRegra=" + idRegra +
				", nome='" + nome + '\'' +
				", descricao='" + descricao + '\'' +
				", estado=" + estado +
				", idUsuario='" + idUsuario + '\'' +
				", idCasa=" + idCasa +
				", data='" + data + '\'' +
				", valor=" + valor +
				'}';
	}
}
