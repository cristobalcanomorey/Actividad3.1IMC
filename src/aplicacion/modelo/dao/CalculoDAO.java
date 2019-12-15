package aplicacion.modelo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.NamingException;

import org.slf4j.Logger;

import aplicacion.modelo.JDBCSingleton;
import aplicacion.modelo.LogSingleton;
import aplicacion.modelo.ejb.CalculosEJB;
import aplicacion.modelo.pojo.Calculo;
import aplicacion.modelo.pojo.Usuario;

/***
 * Gestiona los movimientos de la tabla calculo en la BBDD
 * 
 * @author tofol
 *
 */
public class CalculoDAO {

	private static final Logger LOG = LogSingleton.getInstance().getLoggerCalculoDAO();
	private static final JDBCSingleton CON = JDBCSingleton.getInstance();

	/***
	 * Obtiene los cálculos de un usuario.
	 * 
	 * @param usuario Usuario al que se le busca su historial
	 * @return Historial del usuario
	 */
	public static ArrayList<Calculo> getHistorial(Usuario usuario) {
		ArrayList<Calculo> hist = null;

		if (usuario != null) {
			String query = "SELECT * FROM calculo WHERE idUsuario='" + usuario.getId().toString() + "'";
			try {
				CON.setConnection("java:/comp/env", "jdbc/ActividadIMC");
				if (CON.getConnection() != null) {
					CON.setStatement();
					ResultSet rs = CON.getStatement().executeQuery(query);
					rs.last();
					if (rs.getRow() > 0) {
						rs.beforeFirst();
						hist = CalculosEJB.rsAArrayList(rs);
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
		}
		return hist;
	}

	/***
	 * Añade un calculo.
	 * 
	 * @param usuario Usuario que ha hecho el cálculo
	 * @param calculo Calculo realizado.
	 */
	public static void insertCalculo(Usuario usuario, Calculo calculo) {
		String query = "INSERT INTO calculo (estatura,peso,fecha,idUsuario) values ('" + calculo.getEstatura() + "','"
				+ calculo.getPeso() + "','" + CalculosEJB.fechaAString(calculo.getFecha()) + "','" + usuario.getId()
				+ "')";
		try {
			CON.setConnection("java:/comp/env", "jdbc/ActividadIMC");
			if (CON.getConnection() != null) {
				CON.setStatement();
				CON.getStatement().executeUpdate(query);
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

	}

}
