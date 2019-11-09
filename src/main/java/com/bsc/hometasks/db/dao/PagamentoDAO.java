package com.bsc.hometasks.db.dao;

import com.bsc.hometasks.db.ConnectionFactory;
import com.bsc.hometasks.pojo.Pagamento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class PagamentoDAO {

	public int criaPagamento(Pagamento pagamento) {
		String sql = "insert into Pagamento (idCredor,idDevedor,valor,data,juros,descricao) values (?,?,?,?,?,?);";
		int id = 0;
		try (Connection conexao = ConnectionFactory.getDBConnection();
			 PreparedStatement stmt = conexao.prepareStatement(sql,RETURN_GENERATED_KEYS)) {

			stmt.setString(1, pagamento.getIdCredor());
			stmt.setString(2, pagamento.getIdDevedor());
			stmt.setInt(3, pagamento.getValor());
			stmt.setString(4, pagamento.getData());
			stmt.setInt(5, pagamento.getJuros());
			stmt.setString(6,pagamento.getDescricao());
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

	public int atualizaPagamento(Pagamento pagamento) {
		String sql = "update Pagamento set valor = ?," +
				"data = ?,juros = ?, descricao = ? where idPagamento = ? and idCredor = ? and idDevedor = ?;";
		int rows = 0;
		try (Connection conexao = ConnectionFactory.getDBConnection();
			 PreparedStatement stmt = conexao.prepareStatement(sql)) {

			stmt.setInt(1, pagamento.getValor());
			stmt.setString(2, pagamento.getData());
			stmt.setInt(3, pagamento.getJuros());
			stmt.setString(4,pagamento.getDescricao());
			stmt.setInt(5,pagamento.getIdPagamento());
			stmt.setString(6, pagamento.getIdCredor());
			stmt.setString(7, pagamento.getIdDevedor());
			rows = stmt.executeUpdate();

		} catch (SQLException ex) {
			System.err.println(ex.toString());
		}
		return rows;
	}

	public Pagamento buscaPagamento(int idPagameto, String idCredor, String idDevedor) {
		String sql = "SELECT * from Pagamento where idPagamento = ? and idCredor = ? and idDevedor = ?";

		Pagamento pagamento = null;
		try (Connection conexao = ConnectionFactory.getDBConnection();
			 PreparedStatement stmt = conexao.prepareStatement(sql)) {

			stmt.setInt(1,idPagameto);
			stmt.setString(2,idCredor);
			stmt.setString(3,idDevedor);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				pagamento = new Pagamento(
						rs.getInt("idPagamento"),
						rs.getString("idDevedor"),
						rs.getString("idCredor"),
						rs.getInt("juros"),
						rs.getInt("valor"),
						rs.getString("data"),
						rs.getString("descricao"));
			}
		} catch (SQLException ex) {
			System.err.println(ex.toString());
		}
		return pagamento;
	}

	public int removePagamento(Pagamento pagamento) {
		String sql = "DELETE from Pagamento where idPagamento = ? and idCredor = ? and idDevedor = ?";
		int rows = 0;
		try (Connection conexao = ConnectionFactory.getDBConnection();
			 PreparedStatement stmt = conexao.prepareStatement(sql)) {

			stmt.setInt(1, pagamento.getIdPagamento());
			stmt.setString(2,pagamento.getIdCredor());
			stmt.setString(3,pagamento.getIdDevedor());
			rows = stmt.executeUpdate();

		} catch (SQLException ex) {
			System.err.println(ex.toString());
		}
		return rows;
	}

}
