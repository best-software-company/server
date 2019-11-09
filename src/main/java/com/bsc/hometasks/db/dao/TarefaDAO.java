package com.bsc.hometasks.db.dao;

import com.bsc.hometasks.db.ConnectionFactory;
import com.bsc.hometasks.pojo.Tarefa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class TarefaDAO {

	public int criaTarefa(Tarefa tarefa) {
		String sql = "insert into Tarefa (nome,descricao,data,valor,idRelator,idResponsavel,estado) " +
				"values (?,?,?,?,?,?,?);";
		int id = 0;
		try (Connection conexao = ConnectionFactory.getDBConnection();
			 PreparedStatement stmt = conexao.prepareStatement(sql,RETURN_GENERATED_KEYS)) {

			stmt.setString(1, tarefa.getNome());
			stmt.setString(2, tarefa.getDescricao());
			stmt.setString(3, tarefa.getData());
			stmt.setInt(4, tarefa.getValor());
			stmt.setString(5, tarefa.getIdRelator());
			stmt.setString(6, tarefa.getIdResponsavel());
			stmt.setString(7, tarefa.getEstado());
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

	public int atualizaTarefa(Tarefa tarefa) {
		String sql = "update Tarefa set nome = ?, descricao = ?, data = ?, valor = ?,idRelator = ?," +
				"idResponsavel = ?, estado = ? where idTarefa = ?";
		int rows = 0;
		try (Connection conexao = ConnectionFactory.getDBConnection();
			 PreparedStatement stmt = conexao.prepareStatement(sql)) {

			stmt.setString(1, tarefa.getNome());
			stmt.setString(2, tarefa.getDescricao());
			stmt.setString(3, tarefa.getData());
			stmt.setInt(4, tarefa.getValor());
			stmt.setString(5, tarefa.getIdRelator());
			stmt.setString(6, tarefa.getIdResponsavel());
			stmt.setString(7, tarefa.getEstado());
			stmt.setInt(8, tarefa.getIdTarefa());
			rows = stmt.executeUpdate();

		} catch (SQLException ex) {
			System.err.println(ex.toString());
		}
		return rows;
	}

	public Tarefa buscaTarefa(int idTarefa) {
		String sql = "select * from  Tarefa where idTarefa = ?";
		Tarefa tarefa = null;
		try (Connection conexao = ConnectionFactory.getDBConnection();
			 PreparedStatement stmt = conexao.prepareStatement(sql)) {

				stmt.setInt(1, idTarefa);
				ResultSet rs = stmt.executeQuery();

				if(rs.next()){
					tarefa = new Tarefa(
							rs.getInt("idTarefa"),
							rs.getString("nome"),
							rs.getString("descricao"),
							rs.getString("idResponsavel"),
							rs.getString("idRelator"),
							rs.getString("estado"),
							rs.getString("data"),
							rs.getInt("valor"));
				}

				rs.close();

		} catch (SQLException ex) {
			System.err.println(ex.toString());
		}
		return  tarefa;
	}

	public int removeTarefa(int idTarefa) {
		String sql = "delete from  Tarefa where idTarefa = ?";
		int rows = 0;
		try (Connection conexao = ConnectionFactory.getDBConnection();
			 PreparedStatement stmt = conexao.prepareStatement(sql)) {

			stmt.setInt(1, idTarefa);
			rows = stmt.executeUpdate();

		} catch (SQLException ex) {
			System.err.println(ex.toString());
		}
		return rows;
	}

	/*Retorna uma lista com as tarefas de um usuário em um determinado estado*/
	public List<Tarefa> buscaTarefasUsuarioEstado(String idusuario, String estado) {
		String sql = "select * from  Tarefa where idResponsavel = ? and estado = ?";
		List<Tarefa> tarefa = new ArrayList<>();
		try (Connection conexao = ConnectionFactory.getDBConnection();
			 PreparedStatement stmt = conexao.prepareStatement(sql)) {

			stmt.setString(1, idusuario);
			stmt.setString(2, estado);
			ResultSet rs = stmt.executeQuery();

			while(rs.next()){
				tarefa.add(new Tarefa(
						rs.getInt("idTarefa"),
						rs.getString("nome"),
						rs.getString("descricao"),
						rs.getString("idResponsavel"),
						rs.getString("idRelator"),
						rs.getString("estado"),
						rs.getString("data"),
						rs.getInt("valor")));
			}

			rs.close();

		} catch (SQLException ex) {
			System.err.println(ex.toString());
		}
		return  tarefa;
	}
}
