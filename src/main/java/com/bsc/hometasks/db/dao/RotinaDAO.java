package com.bsc.hometasks.db.dao;

import com.bsc.hometasks.db.ConnectionFactory;
import com.bsc.hometasks.pojo.Rotina;
import com.bsc.hometasks.pojo.Tarefa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class RotinaDAO {
    private TarefaDAO dao = new TarefaDAO();


    public int criaRotina(Rotina rotina) {
        //int ret = dao.criaTarefa(rotina.getTarefa());
//		if (ret == 0){
//			return 0;
//		}
        String sql = "insert into Rotina (validade,alternar,nome,descricao,idUsuario) values (?,?,?,?,?)";
        int id = 0;
        try (Connection conexao = ConnectionFactory.getDBConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql, RETURN_GENERATED_KEYS)) {

            stmt.setString(1, rotina.getValidade());
            stmt.setBoolean(2, rotina.isAlternar());
            stmt.setString(3, rotina.getNome());
            stmt.setString(4, rotina.getDescricao());
            stmt.setString(5, rotina.getIdUsuario());

            stmt.execute();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }
            rs.close();

        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        return id;
    }

//	public int atualizaRotina(Rotina rotina) {
//		dao.atualizaTarefa(rotina.getTarefa());
//		String sql = "update Rotina set validade = ?, alternar = ? where idTarefa = ?";
//		int rows = 0;
//		try (Connection conexao = ConnectionFactory.getDBConnection();
//			 PreparedStatement stmt = conexao.prepareStatement(sql,RETURN_GENERATED_KEYS)) {
//
//			stmt.setString(1, rotina.getValidade());
//			stmt.setBoolean(2, rotina.isAlternar());
//			stmt.setInt(3,rotina.getTarefa().getIdTarefa());
//			rows = stmt.executeUpdate();
//
//		} catch (SQLException ex) {
//			System.err.println(ex.toString());
//		}
//		return rows;
//	}

//    public Rotina buscaRotina(int idRotina) {
//        String sql = "select * from  Rotina where idTarefa = ?";
//        Rotina rotina = null;
//        try (Connection conexao = ConnectionFactory.getDBConnection();
//             PreparedStatement stmt = conexao.prepareStatement(sql)) {
//
//            stmt.setInt(1, idRotina);
//            ResultSet rs = stmt.executeQuery();
//
//            if (rs.next()) {
//                Tarefa tarefa = dao.buscaTarefa(rs.getInt("idTarefa"));
//                rotina = new Rotina(
//                        rs.getString("validade"),
//                        rs.getBoolean("alternar"),
//                        tarefa);
//            }
//
//            rs.close();
//
//        } catch (SQLException ex) {
//            System.err.println(ex.toString());
//        }
//        return rotina;
//    }

    public int removeRotina(int idRotina) {
        String sql = "delete from  Rotina where idRotina = ?";
        int rows = 0;
        try (Connection conexao = ConnectionFactory.getDBConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idRotina);
            rows = stmt.executeUpdate();

        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        return rows;
    }

    /*Retorna uma lista com as rotinas de um usuário*/
    public List<Rotina> buscaRotinasUsuario(String idUsuario) {
        List<Rotina> rotinas = new ArrayList<>();

        String sql = "select * from  Rotina where idUsuario = ?";
        try (Connection conexao = ConnectionFactory.getDBConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, idUsuario);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                rotinas.add(new Rotina(
                        rs.getString("validade"),
                        rs.getBoolean("alternar"),
                        rs.getInt("idRotina"),
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getString("idUsuario")
                        ));
            }

            rs.close();

        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }

        return rotinas;
    }

}
