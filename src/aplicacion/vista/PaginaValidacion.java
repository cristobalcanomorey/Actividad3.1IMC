package aplicacion.vista;

import java.io.PrintWriter;

import aplicacion.vista.html.Cuerpo;
import aplicacion.vista.html.Header;
import aplicacion.vista.html.especificos.Html;
import aplicacion.vista.html.especificos.Tag;

public class PaginaValidacion {
	private Html pagina;
	private Header header;
	private Cuerpo cuerpo;

	/***
	 * Crea la página de error de Validacion
	 */
	public PaginaValidacion() {
		this.pagina = new Html("Validacion", "css/style.css", "js/script.js");
		this.header = new Header(null, null);
		this.header.addAPagina(this.pagina);
		Tag error = new Tag("h2", "Tu cuenta ha caducado o ya ha sido validada.", true, true);
		Tag volver = new Tag("a", "Volver a la página principal", true, true);
		volver.prepararAtributos();
		volver.addAtributo("href", "Principal");
		pagina.addABody(volver);
		this.cuerpo = new Cuerpo(null, null, error);
		this.cuerpo.addAPagina(this.pagina);
	}

	public void print(PrintWriter out) {
		out.println(this.pagina.toString());
	}
}
