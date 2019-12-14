package com.bsc.hometasks.db.dao;

import com.bsc.hometasks.db.ConnectionFactory;
import com.bsc.hometasks.pojo.Regra;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class RegraDAO {

	public int criaRegra(Regra regra) {
		String sql = "insert into Regra (descricao,estado,valor,idUsuario,idCasa,data,nome) " +
				"values (?,?,?,?,?,?,?);";
		int id = 0;
		try (Connection conexao = ConnectionFactory.getDBConnection();
			 PreparedStatement stmt = conexao.prepareStatement(sql,RETURN_GENERATED_KEYS)) {

			stmt.setString(1, regra.getDescricao());
			stmt.setBoolean(2,regra.isEstado());
			stmt.setInt(3, regra.getValor());
			stmt.setString(4, regra.getIdUsuario());
			stmt.setInt(5,regra.getIdCasa());
			stmt.setString(6, regra.getData());
			stmt.setString(7, regra.getNome());
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

	public int atualizaRegra(Regra regra) {
		String sql = "update Regra set descricao = ?, estado = ?," +
				"valor = ?, idUsuario = ?, data = ?, nome = ? where idRegra = ?";

		int rows = 0;
		try (Connection conexao = ConnectionFactory.getDBConnection();
			 PreparedStatement stmt = conexao.prepareStatement(sql,RETURN_GENERATED_KEYS)) {

			stmt.setString(1, regra.getDescricao());
			stmt.setBoolean(2,regra.isEstado());
			stmt.setInt(3, regra.getValor());
			stmt.setString(4, regra.getIdUsuario());
			stmt.setString(5, regra.getData());
			stmt.setString(6,regra.getNome());
			stmt.setInt(7,regra.getIdRegra());
			System.out.println("linhas " + rows);
			rows = stmt.executeUpdate();

		} catch (SQLException ex) {
			System.err.println(ex.toString());
		}
		return rows;
	}

	public Regra buscaRegra(int idRegra) {
		String sql = "select * from  Regra where idRegra = ?";
		Regra regra = null;
		try (Connection conexao = ConnectionFactory.getDBConnection();
			 PreparedStatement stmt = conexao.prepareStatement(sql)) {

			stmt.setInt(1, idRegra);
			ResultSet rs = stmt.executeQuery();

			if(rs.next()){
				regra = new Regra(
						rs.getInt("idRegra"),
						rs.getString("nome"),
						rs.getString("descricao"),
						rs.getBoolean("estado"),
						rs.getString("idUsuario"),
						rs.getInt("idCasa"),
						rs.getString("data"),
						rs.getInt("valor"));
			}

			rs.close();

		} catch (SQLException ex) {
			System.err.println(ex.toString());
		}
		return  regra;
	}

	public int removeRegra(int idRegra, int idCasa) {
		String sql = "delete from  Regra where idRegra = ? and idCasa = ?";
		int rows = 0;
		try (Connection conexao = ConnectionFactory.getDBConnection();
			 PreparedStatement stmt = conexao.prepareStatement(sql)) {

			stmt.setInt(1, idRegra);
			stmt.setInt(2, idCasa);
			rows = stmt.executeUpdate();

		} catch (SQLException ex) {
			System.err.println(ex.toString());
		}
		return rows;
	}

	/*Retorna uma lista com as regras da Casa*/
	public List<Regra> buscaRegrasCasa(int idCasa) {
		String sql = "select * from  Regra where idCasa = ?";
		List<Regra> regras = new ArrayList<>();
		try (Connection conexao = ConnectionFactory.getDBConnection();
			 PreparedStatement stmt = conexao.prepareStatement(sql)) {

			stmt.setInt(1, idCasa);
			ResultSet rs = stmt.executeQuery();

			while(rs.next()){
				regras.add(new Regra(
						rs.getInt("idRegra"),
						rs.getString("nome"),
						rs.getString("descricao"),
						rs.getBoolean("estado"),
						rs.getString("idUsuario"),
						rs.getInt("idCasa"),
						rs.getString("data"),
						rs.getInt("valor")));
			}

			rs.close();

		} catch (SQLException ex) {
			System.err.println(ex.toString());
		}
		return  regras;
	}

}
