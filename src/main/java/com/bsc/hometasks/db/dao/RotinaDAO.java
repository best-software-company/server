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

	public int criaRotina(Rotina rotina) {
		String sql = "insert into Rotina (idRotina,validade,alternar) values (?,?,?)";
		int id = 0;
		try (Connection conexao = ConnectionFactory.getDBConnection();
			 PreparedStatement stmt = conexao.prepareStatement(sql,RETURN_GENERATED_KEYS)) {

			stmt.setInt(1, rotina.getIdRotina());
			stmt.setString(2, rotina.getValidade());
			stmt.setBoolean(3, rotina.isAlternar());
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

	public int atualizaRotina(Rotina rotina) {
		String sql = "update Rotina set validade = ?, alternar = ?";
		int rows = 0;
		try (Connection conexao = ConnectionFactory.getDBConnection();
			 PreparedStatement stmt = conexao.prepareStatement(sql,RETURN_GENERATED_KEYS)) {

			stmt.setString(1, rotina.getValidade());
			stmt.setBoolean(2, rotina.isAlternar());
			rows = stmt.executeUpdate();

		} catch (SQLException ex) {
			System.err.println(ex.toString());
		}
		return rows;
	}

	public Rotina buscaRotina(int idRotina) {
		String sql = "select * from  Rotina where idRotina = ?";
		Rotina rotina = null;
		try (Connection conexao = ConnectionFactory.getDBConnection();
			 PreparedStatement stmt = conexao.prepareStatement(sql)) {

			stmt.setInt(1, idRotina);
			ResultSet rs = stmt.executeQuery();

			if(rs.next()){
				rotina = new Rotina(
						rs.getInt("idRotina"),
						rs.getString("validade"),
						rs.getBoolean("alternar"));
			}

			rs.close();

		} catch (SQLException ex) {
			System.err.println(ex.toString());
		}
		return  rotina;
	}

	public int removeRotina (int idRotina){
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
	public List<Rotina> buscaRotinasUsuario(String idUsuario){
		List<Rotina> rotinas = new ArrayList<>();

		TarefaDAO dao = new TarefaDAO();
		List<Tarefa> tarefas = dao.buscaTarefasUsuarioEstado("Roque007","aberta");
		for (Tarefa t : tarefas){
			String sql = "select * from  Rotina where idRotina = ?";
			try (Connection conexao = ConnectionFactory.getDBConnection();
				 PreparedStatement stmt = conexao.prepareStatement(sql)) {

				stmt.setInt(1, t.getIdTarefa());
				ResultSet rs = stmt.executeQuery();

				while(rs.next()){
					rotinas.add(new Rotina(
							rs.getInt("idRotina"),
							rs.getString("validade"),
							rs.getBoolean("alternar")));
				}

				rs.close();

			} catch (SQLException ex) {
				System.err.println(ex.toString());
			}
		}
		return  rotinas;
	}

}
