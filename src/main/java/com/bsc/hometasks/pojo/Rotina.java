package com.bsc.hometasks.pojo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Rotina {
	int idRotina;
	String validade;
	boolean alternar;

	public Rotina() {
	}

	public Rotina(int idRotina, String validade, boolean alternar) {
		this.idRotina = idRotina;
		this.validade = validade;
		this.alternar = alternar;
	}

	public int getIdRotina() {
		return idRotina;
	}

	public void setIdRotina(int idRotina) {
		this.idRotina = idRotina;
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

	@Override
	public String toString() {
		return "Rotina{" +
				"idRotina=" + idRotina +
				", validade='" + validade + '\'' +
				", alternar=" + alternar +
				'}';
	}
}
