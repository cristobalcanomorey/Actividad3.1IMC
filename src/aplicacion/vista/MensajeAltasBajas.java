package aplicacion.vista;

import java.util.ArrayList;

import aplicacion.modelo.ejb.CalculosEJB;
import aplicacion.modelo.pojo.AltaBaja;
import aplicacion.vista.html.especificos.Tabla;
import aplicacion.vista.html.especificos.Tag;

public class MensajeAltasBajas {

	private Tag mensaje = null;
	private Tabla tabla = null;

	/***
	 * Constructor para el mensaje de altas y bajas
	 * 
	 * @param altasBajas
	 */
	public MensajeAltasBajas(ArrayList<AltaBaja> altasBajas) {
		crearTablaMensaje(altasBajas);
		if (tabla != null) {
			mensaje = new Tag("h2", "Estos son los movimientos de la BBDD en los últimos 5 minutos:", true, true);
		} else {
			mensaje = new Tag("h2", "Todo está tranquilo por aquí... Demasiado tranquilo.", true, true);
		}
	}

	/***
	 * Crea la tabla con las altas y las bajas
	 * 
	 * @param altasBajas las altas y las bajas
	 */
	private void crearTablaMensaje(ArrayList<AltaBaja> altasBajas) {
		if (altasBajas != null) {
			tabla = new Tabla();
			tabla.addAFila(new Tag("ID"), "th");
			tabla.addAFila(new Tag("CORREO"), "th");
			tabla.addAFila(new Tag("NOMBRE"), "th");
			tabla.addAFila(new Tag("TIPO DE ACCIÓN"), "th");
			tabla.addAFila(new Tag("FECHA DE ACCIÓN"), "th");
			tabla.addFilaAEncabezado();
			tabla.resetFila();
			tabla.prepararCuerpo();
			for (AltaBaja altaBaja : altasBajas) {
				tabla.addAFila(new Tag(altaBaja.getId().toString()), "td");
				tabla.addAFila(new Tag(altaBaja.getCorreo()), "td");
				tabla.addAFila(new Tag(altaBaja.getNombre()), "td");
				tabla.addAFila(new Tag(altaBaja.getTipoAccion()), "td");
				tabla.addAFila(new Tag(CalculosEJB.fechaAString(altaBaja.getFecha())), "td");
				tabla.addFilaACuerpo();
				tabla.resetFila();
			}
		}
	}

	public Tabla getTabla() {
		return tabla;
	}

	public Tag getMensaje() {
		return mensaje;
	}

}
