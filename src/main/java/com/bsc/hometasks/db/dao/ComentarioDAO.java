package com.bsc.hometasks.db.dao;

import com.bsc.hometasks.db.ConnectionFactory;
import com.bsc.hometasks.pojo.Comentario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class ComentarioDAO {

	public int criaComentario(Comentario comentario) {
		String sql = "insert into Comentario (texto,midia,data,idTarefa,idUsuario) values (?,?,?,?,?)";
		int id = 0;
		try (Connection conexao = ConnectionFactory.getDBConnection();
			 PreparedStatement stmt = conexao.prepareStatement(sql,RETURN_GENERATED_KEYS)) {

			stmt.setString(1,comentario.getTexto());
			stmt.setBlob(2,comentario.getMidia());
			stmt.setString(3,comentario.getData());
			stmt.setInt(4,comentario.getIdTarefa());
			stmt.setString(5,comentario.getIdUsuario());
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

	public int atualizaComentario(Comentario comentario) {
		String sql = "update Comentario set texto = ?, midia = ?, data = ? where " +
				"idComentario = ?";
		int rows = 0;
		try (Connection conexao = ConnectionFactory.getDBConnection();
			 PreparedStatement stmt = conexao.prepareStatement(sql,RETURN_GENERATED_KEYS)) {

			stmt.setString(1,comentario.getTexto());
			stmt.setBlob(2,comentario.getMidia());
			stmt.setString(3,comentario.getData());
			stmt.setInt(4,comentario.getIdComentario());
			rows = stmt.executeUpdate();
		} catch (SQLException ex) {
			System.err.println(ex.toString());
		}
		return rows;
	}

	public Comentario buscaComentario(int idTarefa, String idUsuario) {
		String sql = "select * from Comentario where idUsuario = ? and idTarefa = ?";
		Comentario comentario = null;
		try (Connection conexao = ConnectionFactory.getDBConnection();
			 PreparedStatement stmt = conexao.prepareStatement(sql)) {

			stmt.setString(1,idUsuario);
			stmt.setInt(2,idTarefa);
			ResultSet rs = stmt.executeQuery();

			if(rs.next()){
				comentario = new Comentario(
						rs.getInt("idComentario"),
						rs.getString("texto"),
						rs.getBlob("midia"),
						rs.getString("data"),
						rs.getInt("idTarefa"),
						rs.getString("idUsuario"));
			}


		} catch (SQLException ex) {
			System.err.println(ex.toString());
		}
		return comentario;
	}

	public Comentario buscaComentarioById(int idComentario) {
		String sql = "select * from Comentario where idComentario = ?";
		Comentario comentario = null;
		try (Connection conexao = ConnectionFactory.getDBConnection();
			 PreparedStatement stmt = conexao.prepareStatement(sql)) {

			stmt.setInt(1,idComentario);
			ResultSet rs = stmt.executeQuery();

			if(rs.next()){
				comentario = new Comentario(
						rs.getInt("idComentario"),
						rs.getString("texto"),
						rs.getBlob("midia"),
						rs.getString("data"),
						rs.getInt("idTarefa"),
						rs.getString("idUsuario"));
			}


		} catch (SQLException ex) {
			System.err.println(ex.toString());
		}
		return comentario;
	}

	/*Retorna lista de comentários de uma Tarefa*/
	public List<Comentario> buscaComentariosTarefa(int idTarefa){
		String sql = "select * from Comentario where idTarefa = ?";
		List<Comentario> comentarios = new ArrayList<>();
		try (Connection conexao = ConnectionFactory.getDBConnection();
			 PreparedStatement stmt = conexao.prepareStatement(sql)) {

			stmt.setInt(1,idTarefa);
			ResultSet rs = stmt.executeQuery();

			while(rs.next()){
				comentarios.add(new Comentario(
						rs.getInt("idComentario"),
						rs.getString("texto"),
						rs.getBlob("midia"),
						rs.getString("data"),
						rs.getInt("idTarefa"),
						rs.getString("idUsuario")));
			}


		} catch (SQLException ex) {
			System.err.println(ex.toString());
		}
		return comentarios;

	}
}
