package aplicacion.modelo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.naming.NamingException;

import org.slf4j.Logger;

import aplicacion.modelo.JDBCSingleton;
import aplicacion.modelo.LogSingleton;
import aplicacion.modelo.ejb.UsuariosEJB;
import aplicacion.modelo.pojo.Usuario;

public class AltasBajasDAO {

	private static final Logger LOG = LogSingleton.getInstance().getLoggerAltasBajasDAO();
	private static final JDBCSingleton CON = JDBCSingleton.getInstance();

	public static void registrarValidacion(Usuario usuario) {
		String insert = "INSERT INTO altas_bajas (correo,nombre,tipoAccion,fecha) VALUES ('" + usuario.getCorreo()
				+ "','" + usuario.getNombre() + "','V','" + UsuariosEJB.fechaAString(new Date()) + "')";

		try {
			CON.setConnection("java:/comp/env", "jdbc/ActividadIMC");
			if (CON.getConnection() != null) {
				CON.setStatement();
				CON.getStatement().executeUpdate(insert);
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

	public static ResultSet getAltasBajas() {
		String query = "SELECT * FROM altas_bajas";
		ResultSet rs = null;
		try {
			CON.setConnection("java:/comp/env", "jdbc/ActividadIMC");
			if (CON.getConnection() != null) {
				CON.setStatement();
				rs = CON.getStatement().executeQuery(query);
				rs.last();
				if (rs.getRow() > 0) {
					rs.beforeFirst();
					return rs;
				}
				rs.close();
			}
		} catch (ClassNotFoundException | SQLException | NamingException e) {
			LOG.error("ERROR CALCULO DAO: ", e);
		} finally {
			if (CON.getStatement() != null) {
				try {
					CON.getConnection().close();
				} catch (SQLException e) {
					LOG.error("ERROR CALCULO DAO: ", e);
				}
			}
			if (CON.getConnection() != null) {
				try {
					CON.getConnection().close();
				} catch (SQLException e) {
					LOG.error("ERROR CALCULO DAO: ", e);
				}
			}
		}
		return rs;
	}

	public static void vaciar() {
		String truncar = "TRUNCATE TABLE altas_bajas";
		try {
			CON.setConnection("java:/comp/env", "jdbc/ActividadIMC");
			if (CON.getConnection() != null) {
				CON.setStatement();
				CON.getStatement().executeUpdate(truncar);
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
