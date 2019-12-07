package aplicacion.controlador;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogSingleton {
	private static final LogSingleton INSTANCE = new LogSingleton();
	private Logger loggerPrincipal = (Logger) LoggerFactory.getLogger(Principal.class);
	
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
	
}
