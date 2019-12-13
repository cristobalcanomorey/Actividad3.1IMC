package aplicacion.modelo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import aplicacion.controlador.Historial;
import aplicacion.controlador.Login;
import aplicacion.controlador.Logout;
import aplicacion.controlador.Principal;
import aplicacion.modelo.dao.CalculoDAO;
import aplicacion.modelo.dao.UsuarioDAO;

public class LogSingleton {
	private static final LogSingleton INSTANCE = new LogSingleton();
	private Logger loggerPrincipal = LoggerFactory.getLogger(Principal.class);
	private Logger loggerLogin = LoggerFactory.getLogger(Login.class);
	private Logger loggerUsuarioDAO = LoggerFactory.getLogger(UsuarioDAO.class);
	private Logger loggerHistorial = LoggerFactory.getLogger(Historial.class);
	private Logger loggerCalculoDAO = LoggerFactory.getLogger(CalculoDAO.class);
	private Logger loggerLogout = LoggerFactory.getLogger(Logout.class);

	/**
	 * Constructor privado
	 */
	private LogSingleton() {
	}

	/**
	 * Obtener instancia
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

	/***
	 * Obtener el logger para Historial
	 * 
	 * @return Logger
	 */
	public Logger getLoggerHistorial() {
		return loggerHistorial;
	}

	/**
	 * Obtener el logger para UsuarioDAO
	 * 
	 * @return Logger
	 */
	public Logger getLoggerUsuarioDAO() {
		return loggerUsuarioDAO;
	}

	/***
	 * Obtener el logger para CalculoDAO
	 * 
	 * @return Logger
	 */
	public Logger getLoggerCalculoDAO() {
		return loggerCalculoDAO;
	}

	/***
	 * Obtener el logger para Logout
	 * 
	 * @return Logger
	 */
	public Logger getLoggerLogout() {
		return loggerLogout;
	}

}
