package com.bsc.hometasks.pojo;

import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Blob;

@XmlRootElement
public class Casa {

	private int idCasa;
	private String nome;
	private String endereco;
	private int aluguel;
	private String descricao;
	private Blob foto;

	public Casa() {}

	public Casa(int idCasa, String nome, String endereco, int aluguel, String descricao, Blob foto) {
		this.idCasa = idCasa;
		this.nome = nome;
		this.endereco = endereco;
		this.aluguel = aluguel;
		this.descricao = descricao;
		this.foto = foto;
	}

	public int getIdCasa() {
		return idCasa;
	}

	public void setIdCasa(int idCasa) {
		this.idCasa = idCasa;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public int getAluguel() {
		return aluguel;
	}

	public void setAluguel(int aluguel) {
		this.aluguel = aluguel;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Blob getFoto() {
		return foto;
	}

	public void setFoto(Blob foto) {
		this.foto = foto;
	}

	@Override
	public String toString() {
		return "Casa{" +
				"idCasa=" + idCasa +
				", nome='" + nome + '\'' +
				", endereco='" + endereco + '\'' +
				", aluguel=" + aluguel +
				", descricao='" + descricao + '\'' +
				", foto=" + foto +
				'}';
	}
}
