package com.bsc.hometasks.pojo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Convite {
	private String idConvidado;
	private String idConvidante;
	private int idCasa;
	private boolean sentido;
	private boolean estado;


	public Convite(String idConvidado, String idConvidante, int idCasa, boolean sentido, boolean estado) {
		this.idConvidado = idConvidado;
		this.idConvidante = idConvidante;
		this.idCasa = idCasa;
		this.sentido = sentido;
		this.estado = estado;
	}

	public String getIdConvidado() {
		return idConvidado;
	}

	public void setIdConvidado(String idConvidado) {
		this.idConvidado = idConvidado;
	}

	public String getIdConvidante() {
		return idConvidante;
	}

	public void setIdConvidante(String idConvidante) {
		this.idConvidante = idConvidante;
	}

	public int getIdCasa() {
		return idCasa;
	}

	public void setIdCasa(int idCasa) {
		this.idCasa = idCasa;
	}

	public boolean isSentido() {
		return sentido;
	}

	public void setSentido(boolean sentido) {
		this.sentido = sentido;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "Convite{" +
				"idConvidado='" + idConvidado + '\'' +
				", idConvidante='" + idConvidante + '\'' +
				", idCasa=" + idCasa +
				", sentido=" + sentido +
				", estado=" + estado +
				'}';
	}
}
