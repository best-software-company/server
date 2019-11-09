package com.bsc.hometasks.db.dao;

import com.bsc.hometasks.db.ConnectionFactory;
import com.bsc.hometasks.pojo.Convite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class ConviteDAO {

	public int criaConvite(Convite convite) {
		String sql = "insert into Convite (idConvidado,idConvidante,sentido,estado,idCasa) values (?,?,?,?,?)";
		int id = 0;
		try (Connection conexao = ConnectionFactory.getDBConnection();
			 PreparedStatement stmt = conexao.prepareStatement(sql,RETURN_GENERATED_KEYS)) {

			stmt.setString(1,convite.getIdConvidado());
			stmt.setString(2,convite.getIdConvidante());
			stmt.setBoolean(3,convite.isSentido());
			stmt.setBoolean(4,convite.isEstado());
			stmt.setInt(5,convite.getIdCasa());
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

	public int atualizaConvite(Convite convite) {
		String sql = "update Convite set sentido = ?, estado = ? where idConvidante = ? " +
				"and idConvidado = ? and idCasa = ?";
		int rows = 0;
		try (Connection conexao = ConnectionFactory.getDBConnection();
			 PreparedStatement stmt = conexao.prepareStatement(sql,RETURN_GENERATED_KEYS)) {

			stmt.setBoolean(1,convite.isSentido());
			stmt.setBoolean(2,convite.isEstado());
			stmt.setString(3,convite.getIdConvidante());
			stmt.setString(4,convite.getIdConvidado());
			stmt.setInt(5,convite.getIdCasa());
			stmt.executeUpdate();


			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()){
				rows = rs.getInt(1);
			}
			rs.close();

		} catch (SQLException ex) {
			System.err.println(ex.toString());
		}
		return rows;
	}

	public Convite buscaConvite(String idConvidado, String idConvidante, int idCasa) {
		String sql = "select * from Convite where idConvidante = ? " +
				"and idConvidado = ? and idCasa = ?";

		Convite convite = null;
		try (Connection conexao = ConnectionFactory.getDBConnection();
			 PreparedStatement stmt = conexao.prepareStatement(sql)) {

			stmt.setString(1, idConvidante);
			stmt.setString(2, idConvidado);
			stmt.setInt(3, idCasa);
			ResultSet rs = stmt.executeQuery();

			if(rs.next()){
				convite = new Convite(
						rs.getString("idConvidado"),
						rs.getString("idConvidante"),
						rs.getInt("idCasa"),
						rs.getBoolean("sentido"),
						rs.getBoolean("estado"));
			}

		} catch (SQLException ex) {
			System.err.println(ex.toString());
		}
		return convite;
	}

	public int removeConvite(String idConvidado, String idConvidante, int idCasa) {
		String sql = "delete from Convite where idConvidante = ? " +
				"and idConvidado = ? and idCasa = ?";

		int rows = 0;
		try (Connection conexao = ConnectionFactory.getDBConnection();
			 PreparedStatement stmt = conexao.prepareStatement(sql)) {

			stmt.setString(1, idConvidante);
			stmt.setString(2, idConvidado);
			stmt.setInt(3, idCasa);
			rows = stmt.executeUpdate();

		} catch (SQLException ex) {
			System.err.println(ex.toString());
		}
		return rows;
	}

	/*Retorna Lista de convites de um usuario*/
	public List<Convite> buscaConvitesUsuario(String idUsuario, boolean estado){
		List<Convite> convites = new ArrayList<>();
		String sql = "select * from Convite where (idConvidado = ? or idConvidante = ?) and estado = ?";

		try (Connection conexao = ConnectionFactory.getDBConnection();
			 PreparedStatement stmt = conexao.prepareStatement(sql)) {

			stmt.setString(1, idUsuario);
			stmt.setString(2, idUsuario);
			stmt.setBoolean(3, estado);
			ResultSet rs = stmt.executeQuery();

			while(rs.next()){
				convites.add(new Convite(
						rs.getString("idConvidado"),
						rs.getString("idConvidante"),
						rs.getInt("idCasa"),
						rs.getBoolean("sentido"),
						rs.getBoolean("estado")));
			}

		} catch (SQLException ex) {
			System.err.println(ex.toString());
		}

		return convites;
	}
}
