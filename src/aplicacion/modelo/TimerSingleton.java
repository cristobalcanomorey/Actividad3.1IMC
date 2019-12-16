package aplicacion.modelo;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;

import aplicacion.modelo.ejb.AltasBajasEJB;
import aplicacion.modelo.ejb.MailEJB;
import aplicacion.modelo.ejb.UsuariosEJB;
import aplicacion.modelo.pojo.AltaBaja;
import aplicacion.modelo.pojo.Mail;
import aplicacion.vista.MensajeAltasBajas;

@Singleton
public class TimerSingleton {

	@EJB
	UsuariosEJB usuariosEJB;

	@EJB
	MailEJB mailEJB;

	@EJB
	AltasBajasEJB altasBajasEJB;

	private final String CORREO_JEFE = "tribot@gmail.com";

	@Schedule(hour = "0", minute = "*/5", second = "0", persistent = false)
	public void corre() {
		LogSingleton log = LogSingleton.getInstance();
		usuariosEJB.limpiar();
		try {
			ArrayList<AltaBaja> altsBajs = altasBajasEJB.getAltasBajas();
			MensajeAltasBajas mensajeAltasBajas = new MensajeAltasBajas(altsBajs);
			enviarCorreo(mensajeAltasBajas);
			altasBajasEJB.vaciar();
		} catch (SQLException e) {
			log.getLoggerTimerSingleton().error("Se ha producido un error en TimerSingleton: ", e);
		}
	}

	private boolean enviarCorreo(MensajeAltasBajas mensajeAltasBajas) {
		Mail correo = mailEJB.getMail("smtp.gmail.com", 587, "imcpractica@gmail.com", "contrasenyaimc");
		String tabla = "";
		if (mensajeAltasBajas.getTabla() != null) {
			tabla = mensajeAltasBajas.getTabla().toString();
		}
		return mailEJB.sendMail(CORREO_JEFE, mensajeAltasBajas.getMensaje().toString(), tabla, correo);
	}
}
