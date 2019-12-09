package aplicacion.modelo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.NamingException;

import org.slf4j.Logger;

import aplicacion.modelo.JDBCSingleton;
import aplicacion.modelo.LogSingleton;
import aplicacion.modelo.pojo.Usuario;

public class UsuarioDAO {

	private static final Logger LOG = (Logger) LogSingleton.getInstance().getLoggerUsuarioDAO();
	private static final JDBCSingleton CON = JDBCSingleton.getInstance();

	/***
	 * Comprueba si existe un usuario y devuelve su información
	 * 
	 * @param correo El correo del usuario
	 * @param paswd  La contraseña del usuario
	 * @return Un objeto Usuario con la información del usuario si existe o null.
	 */
	public Usuario existeUsuario(String correo, String paswd) {
		Usuario usuario = null;

		String query = "SELECT * FROM usuario WHERE correo='" + correo + "' AND password='" + paswd + "'";
		if (correo != null && paswd != null) {
			try {
				CON.setConnection("java:/comp/env", "jdbc/ActividadIMC");
				if (CON.getConnection() != null) {
					CON.setStatement();
					ResultSet rs = CON.getStatement().executeQuery(query);
					rs.last();
					if (rs.getRow() > 0) {
						rs.first();
						usuario = new Usuario(rs.getInt("id"), rs.getString("correo"), rs.getString("nombre"),
								rs.getString("password"), rs.getString("foto"), rs.getBoolean("validado"),
								rs.getDate("fechaRegistro"));
					}
					rs.close();
				}
			} catch (ClassNotFoundException | SQLException | NamingException e) {
				LOG.error("ERROR DAO: ", e);
			} finally {
				if (CON.getStatement() != null) {
					try {
						CON.getConnection().close();
					} catch (SQLException e) {
						LOG.error("ERROR DAO: ", e);
					}
				}
				if (CON.getConnection() != null) {
					try {
						CON.getConnection().close();
					} catch (SQLException e) {
						LOG.error("ERROR DAO: ", e);
					}
				}
			}
		}
		return usuario;
	}

}
