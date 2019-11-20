package com.bsc.hometasks.db.dao;

import com.bsc.hometasks.db.ConnectionFactory;
import com.bsc.hometasks.pojo.Casa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class CasaDAO {

	public int criaCasa(Casa casa) {
		String sql = "insert into Casa (nome,descricao,aluguel,endereco) values (?,?,?,?,?);";
		int id = 0;
		// Try-with-resources irá fechar automaticamente a conexão
		try (Connection conexao = ConnectionFactory.getDBConnection();
			 PreparedStatement stmt = conexao.prepareStatement(sql,RETURN_GENERATED_KEYS)) {

			stmt.setString(1, casa.getNome());
			stmt.setString(2, casa.getDescricao());
			stmt.setInt(3, casa.getAluguel());
			stmt.setString(4, casa.getEndereco());
			stmt.setBlob(5,casa.getFoto());
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

	public int atualizaCasa(Casa casa) {
		String sql = "update Casa set nome = ?, descricao = ?, aluguel = ?, endereco = ?" +
				" where idCasa = ?";
		int rows = 0;
		try (Connection conexao = ConnectionFactory.getDBConnection();
			 PreparedStatement stmt = conexao.prepareStatement(sql,RETURN_GENERATED_KEYS)) {

			stmt.setString(1, casa.getNome());
			stmt.setString(2, casa.getDescricao());
			stmt.setInt(3, casa.getAluguel());
			stmt.setString(4, casa.getEndereco());
			stmt.setInt(5, casa.getIdCasa());

			rows = stmt.executeUpdate();
		} catch (SQLException ex) {
			System.err.println(ex.toString());
		}
		return rows;
	}

	public Casa buscaCasa(int id) {
		String sql = "SELECT * from Casa where idCasa = "+id;

		Casa casa = null;
		try (Connection conexao = ConnectionFactory.getDBConnection();
			 PreparedStatement stmt = conexao.prepareStatement(sql);
			 ResultSet rs = stmt.executeQuery()) {
			if (rs.next()) {
				casa = new Casa(
						rs.getInt("idCasa"),
						rs.getString("nome"),
						rs.getString("endereco"),
						rs.getInt("aluguel"),
						rs.getString("descricao"),
						rs.getBlob("foto"));
			}
		} catch (SQLException ex) {
			System.err.println(ex.toString());
		}

		return casa;
	}

	public int removeCasa(int id) {
		String sql = "DELETE from Casa where idCasa = ?";
		int rows = 0;
		try (Connection conexao = ConnectionFactory.getDBConnection();
			 PreparedStatement stmt = conexao.prepareStatement(sql)) {

			stmt.setInt(1, id);
			rows = stmt.executeUpdate();

		} catch (SQLException ex) {
			System.err.println(ex.toString());
		}

		return rows;
	}

	/*Retorna Lista de Casas com o nome parecido com o parâmetro 'nome' de entrada*/
	public List<Casa> buscaCasasNome(String nome) {
		String sql = "SELECT * from Casa where nome like '%"+nome+"%' limit 100";
		List<Casa> casas = new ArrayList<>();
		try (Connection conexao = ConnectionFactory.getDBConnection();
			 PreparedStatement stmt = conexao.prepareStatement(sql);
			 ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				casas.add(new Casa(
						rs.getInt("idCasa"),
						rs.getString("nome"),
						rs.getString("endereco"),
						rs.getInt("aluguel"),
						rs.getString("descricao"),
						rs.getBlob("foto")));
			}
		} catch (SQLException ex) {
			System.err.println(ex.toString());
		}
		return casas;
	}

	/*Retorna Lista de Casas com o endereço parecido com o parâmetro 'endereco' de entrada*/
	public List<Casa> buscaCasasEndereco(String endereco) {
		String sql = "SELECT * from Casa where endereco like '%"+endereco+"%' limit 100";
		List<Casa> casas = new ArrayList<>();
		try (Connection conexao = ConnectionFactory.getDBConnection();
			 PreparedStatement stmt = conexao.prepareStatement(sql);
			 ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				casas.add(new Casa(
						rs.getInt("idCasa"),
						rs.getString("nome"),
						rs.getString("endereco"),
						rs.getInt("aluguel"),
						rs.getString("descricao"),
						rs.getBlob("foto")));
			}
		} catch (SQLException ex) {
			System.err.println(ex.toString());
		}
		return casas;
	}

}
