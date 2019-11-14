package com.bsc.hometasks.pojo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Pagamento {

	private int idPagamento;

	public Pagamento() {
	}

	private String idDevedor;
	private String idCredor;
	private int juros;
	private int valor;
	private String data;
	private String descricao;

	public Pagamento(int idPagamento, String idDevedor, String idCredor, int juros, int valor, String data, String descricao) {
		this.idPagamento = idPagamento;
		this.idDevedor = idDevedor;
		this.idCredor = idCredor;
		this.juros = juros;
		this.valor = valor;
		this.data = data;
		this.descricao = descricao;
	}

	public Pagamento(String idDevedor, String idCredor, int juros, int valor, String data, String descricao) {
		this.idDevedor = idDevedor;
		this.idCredor = idCredor;
		this.juros = juros;
		this.valor = valor;
		this.data = data;
		this.descricao = descricao;
	}

	public String getIdDevedor() {
		return idDevedor;
	}

	public void setIdDevedor(String idDevedor) {
		this.idDevedor = idDevedor;
	}

	public String getIdCredor() {
		return idCredor;
	}

	public void setIdCredor(String idCredor) {
		this.idCredor = idCredor;
	}

	public int getJuros() {
		return juros;
	}

	public void setJuros(int juros) {
		this.juros = juros;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getIdPagamento() {
		return idPagamento;
	}

	public void setIdPagamento(int idPagamento) {
		this.idPagamento = idPagamento;
	}

	@Override
	public String toString() {
		return "Pagamento{" +
				"idPagamento=" + idPagamento +
				", idDevedor='" + idDevedor + '\'' +
				", idCredor='" + idCredor + '\'' +
				", juros=" + juros +
				", valor=" + valor +
				", data='" + data + '\'' +
				", descricao='" + descricao + '\'' +
				'}';
	}
}
