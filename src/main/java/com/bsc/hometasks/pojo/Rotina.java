package com.bsc.hometasks.pojo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Rotina {
	String validade;
	boolean alternar;
	Tarefa tarefa;

	public Rotina() {
	}


	public Rotina(String validade, boolean alternar, Tarefa tarefa) {
		this.validade = validade;
		this.alternar = alternar;
		this.tarefa = tarefa;
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

	public Tarefa getTarefa() {
		return tarefa;
	}

	public void setTarefa(Tarefa tarefa) {
		this.tarefa = tarefa;
	}

	@Override
	public String toString() {
		return "Rotina{" +
				"validade='" + validade + '\'' +
				", alternar=" + alternar +
				", tarefa=" + tarefa +
				'}';
	}
}
