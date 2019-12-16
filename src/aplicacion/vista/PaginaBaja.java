package aplicacion.vista;

import java.io.PrintWriter;

import aplicacion.vista.html.Cuerpo;
import aplicacion.vista.html.Header;
import aplicacion.vista.html.especificos.Formulario;
import aplicacion.vista.html.especificos.Html;
import aplicacion.vista.html.especificos.Tag;

public class PaginaBaja {
	private Html pagina;
	private Header header;
	private Cuerpo cuerpo;

	/***
	 * Crea la página para darse de baja
	 */
	public PaginaBaja() {
		this.pagina = new Html("Login", "css/style.css", "js/script.js");
		this.header = new Header(null, null);
		this.header.addAPagina(this.pagina);
		pagina.addABody(new Tag("h1", "Darse de baja", true, true));
		Formulario form = crearFormBaja();
		Tag mensaje = new Tag("p", "¿Seguro que quieres darte de baja? Se borrarán todos tus datos.", true, true);
		this.cuerpo = new Cuerpo(form, null, mensaje);
		this.cuerpo.addAPagina(this.pagina);
	}

	/***
	 * Crea el formulario para darse de baja
	 * 
	 * @return
	 */
	private Formulario crearFormBaja() {
		Formulario form = new Formulario("POST", "Baja");

		// Añade el botón de enviar
		Tag enviar = new Tag("input", null, false, false);
		enviar.prepararAtributos();
		enviar.addAtributo("type", "submit");
		enviar.addAtributo("value", "Estoy seguro, dejame salír.");
		form.addItem(enviar);
		return form;
	}

	public void print(PrintWriter out) {
		out.println(this.pagina.toString());
	}
}
