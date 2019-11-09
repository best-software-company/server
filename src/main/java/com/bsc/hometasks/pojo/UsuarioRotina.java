package com.bsc.hometasks.pojo;

import javax.xml.bind.annotation.XmlRootElement;

/*Esta classe representa a tabela UsuarioRotina, esta guarda o historico das realizaçoes de Rotina de um Usuario*/

@XmlRootElement
public class UsuarioRotina {
    private String idUsuario;
    private int idRotina;
    private String data;
    private boolean estado;

    public UsuarioRotina(String idUsuario, int idRotina, String data, boolean estado) {
        this.idUsuario = idUsuario;
        this.idRotina = idRotina;
        this.data = data;
        this.estado = estado;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdRotina() {
        return idRotina;
    }

    public void setIdRotina(int idRotina) {
        this.idRotina = idRotina;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "UsuarioRotina{" +
                "idUsuario='" + idUsuario + '\'' +
                ", idRotina=" + idRotina +
                ", data='" + data + '\'' +
                ", estado=" + estado +
                '}';
    }
}
