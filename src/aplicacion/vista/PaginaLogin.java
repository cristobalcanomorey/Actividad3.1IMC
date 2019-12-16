package aplicacion.vista;

import java.io.PrintWriter;

import aplicacion.vista.html.Cuerpo;
import aplicacion.vista.html.Header;
import aplicacion.vista.html.especificos.Formulario;
import aplicacion.vista.html.especificos.Html;
import aplicacion.vista.html.especificos.Tag;

public class PaginaLogin {

	private Html pagina;
	private Header header;
	private Cuerpo cuerpo;

	/***
	 * Crea la página de login
	 * 
	 * @param error Error del usuario
	 */
	public PaginaLogin(String error) {
		this.pagina = new Html("Login", "css/style.css", "js/script.js");
		this.header = new Header(null, null);
		this.header.addAPagina(this.pagina);
		pagina.addABody(new Tag("h1", "Iniciar sesión", true, true));
		Formulario form = crearFormLogin();
		this.cuerpo = new Cuerpo(form, null, null);
		this.cuerpo.addAPagina(this.pagina);
		if (error != null) {
			this.pagina.addABody(new Tag("p", error, true, true));
		}
	}

	/***
	 * Crea el formulario para iniciar sesión
	 * 
	 * @return
	 */
	private Formulario crearFormLogin() {
		Formulario form = new Formulario("POST", "Login");

		// Añade el input para correo
		form.addItem(new Tag("p", "Correo", true, true));
		Tag correo = new Tag("input", null, false, false);
		correo.prepararAtributos();
		correo.addAtributo("type", "email");
		correo.addAtributo("name", "correo");
		form.addItem(correo);

		// Añade el input para contraseña
		form.addItem(new Tag("p", "Contraseña", true, true));
		Tag password = new Tag("input", null, false, false);
		password.prepararAtributos();
		password.addAtributo("type", "password");
		password.addAtributo("name", "paswd");
		form.addItem(password);

		// Añade el botón de enviar
		Tag enviar = new Tag("input", null, false, false);
		enviar.prepararAtributos();
		enviar.addAtributo("type", "submit");
		enviar.addAtributo("value", "Iniciar sesión");
		form.addItem(enviar);
		return form;
	}

	public void print(PrintWriter out) {
		out.println(this.pagina.toString());
	}
}
