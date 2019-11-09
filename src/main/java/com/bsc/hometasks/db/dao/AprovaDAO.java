package com.bsc.hometasks.db.dao;

import com.bsc.hometasks.db.ConnectionFactory;
import com.bsc.hometasks.pojo.Aprova;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class AprovaDAO {

    public int criaAprova(Aprova aprova){
        String sql = "insert into Aprova (idUsuario,idRegra,estado) values (?,?,?)";
        int id = 0;
        try (Connection conexao = ConnectionFactory.getDBConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql,RETURN_GENERATED_KEYS)) {

            stmt.setString(1,aprova.getIdUsuario());
            stmt.setInt(2,aprova.getIdRegra());
            stmt.setBoolean(3,aprova.isEstado());
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

    public int atualizaAprova(Aprova aprova){
        String sql = "update Aprova set estado = ? where idUsuario = ? and idRegra = ?";
        int rows = 0;
        try (Connection conexao = ConnectionFactory.getDBConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setBoolean(1,aprova.isEstado());
            stmt.setString(2,aprova.getIdUsuario());
            stmt.setInt(3,aprova.getIdRegra());
            rows = stmt.executeUpdate();



        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        return rows;
    }

    public Aprova buscaAprova(String idUsuario, int idRegra){
        String sql = "select * from Aprova where idUsuario = ? and idRegra = ?";
        Aprova aprova = null;
        try (Connection conexao = ConnectionFactory.getDBConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1,idUsuario);
            stmt.setInt(2,idRegra);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                aprova = new Aprova(
                        rs.getString("idUsuario"),
                        rs.getInt("idRegra"),
                        rs.getBoolean("estado"));
            }


        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        return aprova;
    }

    /*
    Retorna lista de aprovação de uma determinada Regra
    */
    public List<Aprova> buscaAprovaRegra(int idRegra){
        String sql = "select * from Aprova where idRegra = ?";
        List<Aprova> aprovas = new ArrayList<>();
        try (Connection conexao = ConnectionFactory.getDBConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1,idRegra);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                aprovas.add(new Aprova(
                        rs.getString("idUsuario"),
                        rs.getInt("idRegra"),
                        rs.getBoolean("estado")));
            }


        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        return aprovas;
    }




}
