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

/***
 * Gestiona los movimientos de la tabla usuario en la BBDD
 * 
 * @author tofol
 *
 */
public class UsuarioDAO {

	private static final Logger LOG = LogSingleton.getInstance().getLoggerUsuarioDAO();
	private static final JDBCSingleton CON = JDBCSingleton.getInstance();

	/***
	 * Comprueba si existe un usuario y devuelve su información
	 * 
	 * @param correo El correo del usuario
	 * @param paswd  La contraseña del usuario
	 * @return Un objeto Usuario con la información del usuario si existe o null.
	 */
	public static Usuario existeUsuario(String correo, String paswd) {
		Usuario usuario = null;

		if (correo != null && paswd != null) {
			String query = "SELECT * FROM usuario WHERE correo='" + correo + "' AND password='" + paswd + "'";
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
		return usuario;
	}

	/***
	 * Comprueba si hay un usuario con ese correo.
	 * 
	 * @param correo Correo que se busca.
	 * @return True si lo ha encontrado, false si no.
	 */
	public static boolean existeUsuario(String correo) {
		boolean existe = false;
		if (correo != null) {
			String query = "SELECT * FROM usuario WHERE correo='" + correo + "'";
			try {
				CON.setConnection("java:/comp/env", "jdbc/ActividadIMC");
				if (CON.getConnection() != null) {
					CON.setStatement();
					ResultSet rs = CON.getStatement().executeQuery(query);
					rs.last();
					if (rs.getRow() > 0) {
						existe = true;
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
		return existe;
	}

	/***
	 * Añade un usuario a la tabla
	 * 
	 * @param nuevo El nuevo usuario
	 */
	public static void insertUsuario(Usuario nuevo) {
		int esValidado = nuevo.isValidado() ? 1 : 0;
		String queryUsuario = "INSERT INTO usuario (correo,nombre,password,foto,validado,fechaRegistro) VALUES ('"
				+ nuevo.getCorreo() + "','" + nuevo.getNombre() + "','" + nuevo.getPassword() + "','" + nuevo.getFoto()
				+ "','" + esValidado + "','" + UsuariosEJB.fechaAString(new Date()) + "')";
		try {
			CON.setConnection("java:/comp/env", "jdbc/ActividadIMC");
			if (CON.getConnection() != null) {
				CON.setStatement();
				CON.getStatement().executeUpdate(queryUsuario);
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

	/***
	 * Valida a un usuario.
	 * 
	 * @param idUsuario Id del usuario a validar.
	 */
	public static void validarPorId(String idUsuario) {
		String update = "UPDATE usuario SET validado=1 WHERE id=" + idUsuario;
		try {
			CON.setConnection("java:/comp/env", "jdbc/ActividadIMC");
			if (CON.getConnection() != null) {
				CON.setStatement();
				CON.getStatement().executeUpdate(update);
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

	/***
	 * Borra a un usuario.
	 * 
	 * @param usuario Usuario a borrar.
	 */
	public static void delete(Usuario usuario) {
		String delete = "DELETE FROM usuario WHERE id=" + usuario.getId().toString();
		try {
			CON.setConnection("java:/comp/env", "jdbc/ActividadIMC");
			if (CON.getConnection() != null) {
				CON.setStatement();
				CON.getStatement().executeUpdate(delete);
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

	/***
	 * Obtiene un usuario por su id.
	 * 
	 * @param idUsuario Id del usuario.
	 * @return Usuario encontrado.
	 */
	public static Usuario selectPorId(String idUsuario) {
		Usuario usu = null;
		String query = "SELECT * FROM usuario WHERE id=" + idUsuario;
		try {
			CON.setConnection("java:/comp/env", "jdbc/ActividadIMC");
			if (CON.getConnection() != null) {
				CON.setStatement();
				ResultSet rs = CON.getStatement().executeQuery(query);
				rs.last();
				if (rs.getRow() > 0) {
					rs.first();
					usu = new Usuario(rs.getInt("id"), rs.getString("correo"), rs.getString("nombre"),
							rs.getString("password"), rs.getString("foto"), rs.getBoolean("validado"),
							rs.getDate("fechaRegistro"));
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
		return usu;
	}

	/***
	 * Quita a todos los usuarios que aún no han sido validados.
	 */
	public static void limpiar() {
		String delete = "DELETE FROM usuario WHERE validado=0";
		try {
			CON.setConnection("java:/comp/env", "jdbc/ActividadIMC");
			if (CON.getConnection() != null) {
				CON.setStatement();
				CON.getStatement().executeUpdate(delete);
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
