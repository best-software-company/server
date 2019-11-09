package com.bsc.hometasks.db.dao;

import com.bsc.hometasks.db.ConnectionFactory;
import com.bsc.hometasks.pojo.UsuarioRotina;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class UsuarioRotinaDAO {

    public int criaUsuarioRotina (UsuarioRotina usuarioRotina){
        String sql = "insert into UsuarioRotina (idUsuario,idRotina,data,estado) values (?,?,?,?)";
        int id = 0;
        try (Connection conexao = ConnectionFactory.getDBConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql,RETURN_GENERATED_KEYS)) {

            stmt.setString(1,usuarioRotina.getIdUsuario());
            stmt.setInt(2,usuarioRotina.getIdRotina());
            stmt.setString(3,usuarioRotina.getData());
            stmt.setBoolean(4,usuarioRotina.isEstado());
            stmt.execute();


            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()){
                id = rs.getInt(1);
            }
            rs.close();

        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        return id;
    }

    public int autualizaUsuarioRotina(UsuarioRotina usuarioRotina){
        String sql = "update UsuarioRotina set data = ?, estado = ? where idUsuario = ? and idRotina = ?";
        int rows = 0;
        try (Connection conexao = ConnectionFactory.getDBConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql,RETURN_GENERATED_KEYS)) {

            stmt.setString(1,usuarioRotina.getData());
            stmt.setBoolean(2,usuarioRotina.isEstado());
            stmt.setString(3,usuarioRotina.getIdUsuario());
            stmt.setInt(4,usuarioRotina.getIdRotina());

            rows = stmt.executeUpdate();

        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        return rows;
    }

    public UsuarioRotina buscaUsuarioRotina (String idUsuario, int idRotina){
        String sql = "select * from UsuarioRotina where idUsuario = ? and idRotina = ?";

        UsuarioRotina usuarioRotina = null;

        try (Connection conexao = ConnectionFactory.getDBConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql,RETURN_GENERATED_KEYS)) {

            stmt.setString(1,idUsuario);
            stmt.setInt(2,idRotina);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()){
                usuarioRotina = new UsuarioRotina(
                        rs.getString("idUsuario"),
                        rs.getInt("idRotina"),
                        rs.getString("data"),
                        rs.getBoolean("estado"));

            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        return usuarioRotina;
    }

    /*Retorna lista de UsuarioRotina de um usuario*/
    public List<UsuarioRotina> buscaUsuarioRotinaUsuario(String idUsuario){
        String sql = "select * from UsuarioRotina where idUsuario = ?";

        List<UsuarioRotina> usuarioRotinas = new ArrayList<>();

        try (Connection conexao = ConnectionFactory.getDBConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql,RETURN_GENERATED_KEYS)) {

            stmt.setString(1,idUsuario);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                usuarioRotinas.add(new UsuarioRotina(
                        rs.getString("idUsuario"),
                        rs.getInt("idRotina"),
                        rs.getString("data"),
                        rs.getBoolean("estado")));
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        return usuarioRotinas;
    }
}
