package aplicacion.modelo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.NamingException;

import org.slf4j.Logger;

import aplicacion.modelo.JDBCSingleton;
import aplicacion.modelo.LogSingleton;
import aplicacion.modelo.pojo.Usuario;

public class ValidacionDAO {

	private static final Logger LOG = LogSingleton.getInstance().getLoggerValidacionDAO();
	private static final JDBCSingleton CON = JDBCSingleton.getInstance();

	public static void insertValidacion(Usuario usuario, String codigo) {
		String queryValidacion = "INSERT INTO validacion (codigo,idUsuario) VALUES ('" + codigo + "','"
				+ usuario.getId().toString() + "')";
		try {
			CON.setConnection("java:/comp/env", "jdbc/ActividadIMC");
			if (CON.getConnection() != null) {
				CON.setStatement();
				CON.getStatement().executeUpdate(queryValidacion);
			}
		} catch (ClassNotFoundException | SQLException | NamingException e) {
			LOG.error("ERROR USUARIO DAO: ", e);
		} finally {
			if (CON.getStatement() != null) {
				try {
					CON.getConnection().close();
				} catch (SQLException e) {
					LOG.error("ERROR USUARIO DAO: ", e);
				}
			}
			if (CON.getConnection() != null) {
				try {
					CON.getConnection().close();
				} catch (SQLException e) {
					LOG.error("ERROR USUARIO DAO: ", e);
				}
			}
		}
	}

	public static String selectIdUsuario(String codigo) {
		String idUsuario = null;
		if (codigo != null) {
			String query = "SELECT * FROM validacion WHERE codigo='" + codigo + "'";
			try {
				CON.setConnection("java:/comp/env", "jdbc/ActividadIMC");
				if (CON.getConnection() != null) {
					CON.setStatement();
					ResultSet rs = CON.getStatement().executeQuery(query);
					rs.last();
					if (rs.getRow() > 0) {
						rs.first();
						idUsuario = rs.getString("idUsuario");
					}
					rs.close();
				}
			} catch (ClassNotFoundException | SQLException | NamingException e) {
				LOG.error("ERROR USUARIO DAO: ", e);
			} finally {
				if (CON.getStatement() != null) {
					try {
						CON.getConnection().close();
					} catch (SQLException e) {
						LOG.error("ERROR USUARIO DAO: ", e);
					}
				}
				if (CON.getConnection() != null) {
					try {
						CON.getConnection().close();
					} catch (SQLException e) {
						LOG.error("ERROR USUARIO DAO: ", e);
					}
				}
			}
		}
		return idUsuario;
	}

	public static void borrar(String codigo) {
		if (codigo != null) {
			String query = "DELETE FROM validacion WHERE codigo='" + codigo + "'";
			try {
				CON.setConnection("java:/comp/env", "jdbc/ActividadIMC");
				if (CON.getConnection() != null) {
					CON.setStatement();
					CON.getStatement().executeUpdate(query);
				}
			} catch (ClassNotFoundException | SQLException | NamingException e) {
				LOG.error("ERROR USUARIO DAO: ", e);
			} finally {
				if (CON.getStatement() != null) {
					try {
						CON.getConnection().close();
					} catch (SQLException e) {
						LOG.error("ERROR USUARIO DAO: ", e);
					}
				}
				if (CON.getConnection() != null) {
					try {
						CON.getConnection().close();
					} catch (SQLException e) {
						LOG.error("ERROR USUARIO DAO: ", e);
					}
				}
			}
		}
	}
}
