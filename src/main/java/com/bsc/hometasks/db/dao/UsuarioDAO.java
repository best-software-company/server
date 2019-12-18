package com.bsc.hometasks.db.dao;

import com.bsc.hometasks.db.ConnectionFactory;
import com.bsc.hometasks.pojo.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class UsuarioDAO {

	public boolean criaUsuario(Usuario usuario) {
		String sql = "insert into Usuario (idUsuario,data,genero,pontos,perfil,nome,telefone,senha,email)" +
				" values (?,?,?,?,?,?,?,?,?)";

		try (Connection conexao = ConnectionFactory.getDBConnection();
			 PreparedStatement stmt = conexao.prepareStatement(sql,RETURN_GENERATED_KEYS)) {

			stmt.setString(1, usuario.getIdUsuario());
			stmt.setString(2, usuario.getData());
			stmt.setString(3, usuario.getGenero());
			stmt.setInt(4, usuario.getPontos());
			stmt.setString(5, usuario.getPerfil());
			stmt.setString(6, usuario.getNome());
			stmt.setString(7, usuario.getTelefone());
			stmt.setString(8, usuario.getSenha());
			stmt.setString(9, usuario.getEmail());

			stmt.execute();
		} catch (SQLException ex) {
			return false;
			//System.err.println(ex.toString());

		}
		return true;
	}


	public int atualizaUsuario(Usuario usuario) {
		String sql;
		if (usuario.getIdCasa() >0){
			sql = "update Usuario set data = ?, genero = ?, pontos = ?, perfil = ?, nome = ?," +
					"telefone = ?, senha = ?, email = ?, idCasa = ?, foto = ?, " +
					"token = ?, total_tarefas = ?, tarefas_avaliadas = ?  where idUsuario = ?";
		}else {
			sql = "update Usuario set data = ?, genero = ?, pontos = ?, perfil = ?, nome = ?," +
					"telefone = ?, senha = ?, email = ?, foto = ?, " +
					"token = ?, total_tarefas = ?, tarefas_avaliadas = ?  where idUsuario = ?";
		}
		int rows = 0;
		try (Connection conexao = ConnectionFactory.getDBConnection();
			 PreparedStatement stmt = conexao.prepareStatement(sql)) {

			stmt.setString(1, usuario.getData());
			stmt.setString(2, usuario.getGenero());
			stmt.setInt(3, usuario.getPontos());
			stmt.setString(4, usuario.getPerfil());
			stmt.setString(5, usuario.getNome());
			stmt.setString(6, usuario.getTelefone());
			stmt.setString(7, usuario.getSenha());
			stmt.setString(8, usuario.getEmail());
			if(usuario.getIdCasa() >0){
				stmt.setInt(9,usuario.getIdCasa());
				stmt.setBlob(10,usuario.getFoto());
				stmt.setString(11, usuario.getToken());
				stmt.setInt(12, usuario.getTotalTarefas());
				stmt.setInt(13, usuario.getTarefasAvaliadas());
				stmt.setString(14, usuario.getIdUsuario());

			}else {
				stmt.setBlob(9,usuario.getFoto());
				stmt.setString(10, usuario.getToken());
				stmt.setInt(11, usuario.getTotalTarefas());
				stmt.setInt(12, usuario.getTarefasAvaliadas());
				stmt.setString(13, usuario.getIdUsuario());

			}

			rows = stmt.executeUpdate();

		} catch (SQLException ex) {
			System.err.println(ex.toString());
			return -1;
		}
		return rows;
	}

	public Usuario buscaUsuario(String idUsuario) {
		String sql = "SELECT * from Usuario where idUsuario = ?";

		Usuario usuario = null;
		try (Connection conexao = ConnectionFactory.getDBConnection()) {

			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setString(1,idUsuario);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				usuario = new Usuario(
						rs.getString("idUsuario"),
						rs.getString("nome"),
						rs.getString("data"),
						rs.getString("genero"),
						rs.getInt("pontos"),
						rs.getString("telefone"),
						rs.getString("senha"),
						rs.getString("email"),
						rs.getString("perfil"),
						rs.getInt("idCasa"),
						rs.getBlob("foto"),
						rs.getString("token"),
						rs.getInt("total_tarefas"),
						rs.getInt("tarefas_avaliadas"));
			}

			stmt.close();
			rs.close();
		} catch (SQLException ex) {
			System.err.println(ex.toString());
		}

		return usuario;
	}

	public int removeUsuario(String idUsuario) {
		String sql = "delete from Usuario where idUsuario = ?";
		int rows = 0;
		try (Connection conexao = ConnectionFactory.getDBConnection();
			 PreparedStatement stmt = conexao.prepareStatement(sql)) {

			stmt.setString(1, idUsuario);
			rows = stmt.executeUpdate();

		} catch (SQLException ex) {
			System.err.println(ex.toString());
		}
		return rows;
	}

	/*Retorna uma lista com usuarios com o nome parecido com o fornecido*/
	public List<Usuario> buscaUsuariosNome(String nome) {
		String sql = "select * from Usuario where nome like '%"+nome+"%' limit 100";
		ArrayList<Usuario> usuarios = new ArrayList<>();
		try (Connection conexao = ConnectionFactory.getDBConnection();
			 PreparedStatement stmt = conexao.prepareStatement(sql);
			 ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				usuarios.add(new Usuario(
						rs.getString("idUsuario"),
						rs.getString("nome"),
						rs.getString("data"),
						rs.getString("genero"),
						rs.getInt("pontos"),
						rs.getString("telefone"),
						rs.getString("senha"),
						rs.getString("email"),
						rs.getString("perfil"),
						rs.getInt("idCasa"),
						rs.getBlob("foto"),
						rs.getString("token"),
						rs.getInt("total_tarefas"),
						rs.getInt("tarefas_avaliadas")));

			}
		} catch (SQLException ex) {
			System.err.println(ex.toString());
		}
		return usuarios;
	}

	/*Retorna uma lista com usuarios com o login parecido com o fornecido*/
	public List<Usuario> buscaUsuariosId(String idUsuario) {
		String sql = "select * from Usuario where idUsuario like '%"+idUsuario+"%' limit 100";
		ArrayList<Usuario> usuarios = new ArrayList<>();
		try (Connection conexao = ConnectionFactory.getDBConnection();
			 PreparedStatement stmt = conexao.prepareStatement(sql);
			 ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				usuarios.add(new Usuario(
						rs.getString("idUsuario"),
						rs.getString("nome"),
						rs.getString("data"),
						rs.getString("genero"),
						rs.getInt("pontos"),
						rs.getString("telefone"),
						rs.getString("senha"),
						rs.getString("email"),
						rs.getString("perfil"),
						rs.getInt("idCasa"),
						rs.getBlob("foto"),
						rs.getString("token"),
						rs.getInt("total_tarefas"),
						rs.getInt("tarefas_avaliadas")));
			}

		} catch (SQLException ex) {
			System.err.println(ex.toString());
		}
		return usuarios;
	}

	/*Retorna o usuario relacionado ao token*/
	public Usuario buscaUsuarioToken(String token) {
		String sql = "SELECT * from Usuario where token = ?";

		Usuario usuario = null;
		try (Connection conexao = ConnectionFactory.getDBConnection()) {

			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setString(1,token);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				usuario = new Usuario(
						rs.getString("idUsuario"),
						rs.getString("nome"),
						rs.getString("data"),
						rs.getString("genero"),
						rs.getInt("pontos"),
						rs.getString("telefone"),
						rs.getString("senha"),
						rs.getString("email"),
						rs.getString("perfil"),
						rs.getInt("idCasa"),
						rs.getBlob("foto"),
						rs.getString("token"),
						rs.getInt("total_tarefas"),
						rs.getInt("tarefas_avaliadas"));
			}

			stmt.close();
			rs.close();
		} catch (SQLException ex) {
			System.err.println(ex.toString());
		}

		return usuario;
	}

	/*Retorna uma lista com usuarios que estao na casa*/
	public List<Usuario> buscaUsuariosCasa(int idCasa) {
		String sql = "select * from Usuario where idCasa = "+idCasa;
		ArrayList<Usuario> usuarios = new ArrayList<>();
		try (Connection conexao = ConnectionFactory.getDBConnection();
			 PreparedStatement stmt = conexao.prepareStatement(sql);
			 ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				usuarios.add(new Usuario(
						rs.getString("idUsuario"),
						rs.getString("nome"),
						rs.getString("data"),
						rs.getString("genero"),
						rs.getInt("pontos"),
						rs.getString("telefone"),
						rs.getString("senha"),
						rs.getString("email"),
						rs.getString("perfil"),
						rs.getInt("idCasa"),
						rs.getBlob("foto"),
						rs.getString("token"),
						rs.getInt("total_tarefas"),
						rs.getInt("tarefas_avaliadas")));
			}

		} catch (SQLException ex) {
			System.err.println(ex.toString());
		}
		return usuarios;
	}

}
