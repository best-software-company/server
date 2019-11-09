package com.bsc.hometasks.pojo;

/* Esta classe representa a tabela Aprova do banco de dados que guarda o historico de aprovação de uma Regra
*  por um Usuario*/

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Aprova {
    private String idUsuario;
    private int idRegra;
    private boolean estado;

    public Aprova(String idUsuario, int idRegra, boolean estado) {
        this.idUsuario = idUsuario;
        this.idRegra = idRegra;
        this.estado = estado;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdRegra() {
        return idRegra;
    }

    public void setIdRegra(int idRegra) {
        this.idRegra = idRegra;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Aprova{" +
                "idUsuario='" + idUsuario + '\'' +
                ", idRegra=" + idRegra +
                ", estado=" + estado +
                '}';
    }
}
