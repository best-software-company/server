package com.bsc.hometasks.pojo;

import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Blob;

@XmlRootElement
public class Usuario {

	private String idUsuario;
	private String nome;
	private String data;
	private String genero;
	private int pontos;
	private String telefone;
	private String senha;
	private String email;
	private String perfil;
	private int idCasa;
	private Blob foto;
	private String token;
	private int totalTarefas;
	private int tarefasAvaliadas;

	public Usuario() {
	}


	public Usuario(String idUsuario, String nome, String data,
				   String genero, int pontos, String telefone, String senha, String email,
				   String perfil, int idCasa, Blob foto, String token, int totalTarefas, int tarefasAvaliadas) {
		this.idUsuario = idUsuario;
		this.nome = nome;
		this.data = data;
		this.genero = genero;
		this.pontos = pontos;
		this.telefone = telefone;
		this.senha = senha;
		this.email = email;
		this.perfil = perfil;
		this.idCasa = idCasa;
		this.foto = foto;
		this.token = token;
		this.totalTarefas = totalTarefas;
		this.tarefasAvaliadas = tarefasAvaliadas;

	}

	public Usuario(String nome, String data, String genero, int pontos, String telefone, String senha, String email, String perfil) {
		this.nome = nome;
		this.data = data;
		this.genero = genero;
		this.pontos = pontos;
		this.telefone = telefone;
		this.senha = senha;
		this.email = email;
		this.perfil = perfil;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public int getPontos() {
		return pontos;
	}

	public void setPontos(int pontos) {
		this.pontos = pontos;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	public int getIdCasa() {
		return idCasa;
	}

	public void setIdCasa(int idCasa) {
		this.idCasa = idCasa;
	}

	public Blob getFoto() {
		return foto;
	}

	public void setFoto(Blob foto) {
		this.foto = foto;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getTotalTarefas() {
		return totalTarefas;
	}

	public void setTotalTarefas(int totalTarefas) {
		this.totalTarefas = totalTarefas;
	}

	public int getTarefasAvaliadas() {
		return tarefasAvaliadas;
	}

	public void setTarefasAvaliadas(int tarefasAvaliadas) {
		this.tarefasAvaliadas = tarefasAvaliadas;
	}

	@Override
	public String toString() {
		return "Usuario{" +
				"idUsuario='" + idUsuario + '\'' +
				", nome='" + nome + '\'' +
				", data='" + data + '\'' +
				", genero='" + genero + '\'' +
				", pontos=" + pontos +
				", telefone='" + telefone + '\'' +
				", senha='" + senha + '\'' +
				", email='" + email + '\'' +
				", perfil='" + perfil + '\'' +
				", idCasa=" + idCasa +
				", foto=" + foto +
				", token=" + token +
				'}';
	}
}
