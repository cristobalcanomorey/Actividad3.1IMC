package aplicacion.modelo;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Clase singleton para la conexión con la base de datos
 * 
 * @author tofol
 *
 */
public class JDBCSingleton {
	private static final JDBCSingleton INSTANCE = new JDBCSingleton();
	private static Connection conn = null;
	private static Statement stmt = null;
	private static Context initContext;

	/**
	 * Constructor privado
	 */
	private JDBCSingleton() {
	}

	/**
	 * Obtener la instancia del singleton
	 * 
	 * @return
	 */
	public static JDBCSingleton getInstance() {
		return INSTANCE;
	}

	/**
	 * Obtener la conexión
	 * 
	 * @return
	 */
	public Connection getConnection() {
		return conn;
	}

	/**
	 * Establece la conexión con la base de datos
	 * 
	 * @param initC Contexto inicial
	 * @param envC  Base de datos
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws NamingException
	 */
	public void setConnection(String initC, String envC) throws ClassNotFoundException, SQLException, NamingException {
		initContext = new InitialContext();
		Context envContext = (Context) initContext.lookup(initC);
		DataSource ds = (DataSource) envContext.lookup(envC);

		JDBCSingleton.conn = ds.getConnection();
	}

	/**
	 * Obtener el statement
	 * 
	 * @return Statement
	 */
	public Statement getStatement() {
		return stmt;
	}

	/**
	 * Crea un statement
	 * 
	 * @throws SQLException
	 */
	public void setStatement() throws SQLException {
		JDBCSingleton.stmt = conn.createStatement();
	}

}
