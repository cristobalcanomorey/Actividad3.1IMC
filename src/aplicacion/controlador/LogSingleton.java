package aplicacion.controlador;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import aplicacion.modelo.dao.UsuarioDAO;

public class LogSingleton {
	private static final LogSingleton INSTANCE = new LogSingleton();
	private Logger loggerPrincipal = LoggerFactory.getLogger(Principal.class);
	private Logger loggerLogin = LoggerFactory.getLogger(Login.class);
	private Logger loggerUsuarioDAO = LoggerFactory.getLogger(UsuarioDAO.class);

	/**
	 * Constructor privado
	 */
	private LogSingleton() {

	}

	/**
	 * Obtener la conexión
	 * 
	 * @return
	 */
	public static LogSingleton getInstance() {
		return INSTANCE;
	}

	/**
	 * Obtener el logger para Principal
	 * 
	 * @return Logger
	 */
	public Logger getLoggerPrincipal() {
		return loggerPrincipal;
	}

	/**
	 * Obtener el logger para Login
	 * 
	 * @return Logger
	 */
	public Logger getLoggerLogin() {
		return loggerLogin;
	}

	/**
	 * Obtener el logger para UsuarioDAO
	 * 
	 * @return Logger
	 */
	public Object getLoggerUsuarioDAO() {
		return loggerUsuarioDAO;
	}

}
